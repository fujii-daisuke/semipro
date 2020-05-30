package red.semipro.domain.service.establish;

import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.Transfer;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.stripe.repository.refund.RefundRepository;
import red.semipro.domain.stripe.repository.transfer.TransferRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EstablishService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarRepository seminarRepository;
    private final SeminarEntryRepository seminarEntryRepository;
    private final TransferRepository transferRepository;
    private final RefundRepository refundRepository;
    private final EmailSharedService emailSharedService;

    @Value("${custom.application.web-root}")
    private String WEB_ROOT;

    public void establish(@Nonnull final Long seminarId, @Nonnull final LocalDate executionDate)
        throws StripeException {

        log.info("セミナーID: " + seminarId + "の開催可否処理を開始します");

        final Seminar seminar = seminarSharedService.findOneWithDetailsForUpdate(
            SeminarCriteria.builder()
                .id(seminarId)
                .openingStatus(OpeningStatus.OPENING)
                .build());

        // check date
        if (executionDate.isBefore(seminar.getGoal().getEntryEndingAt())
            || executionDate.isEqual(seminar.getGoal().getEntryEndingAt())) {
            log.info("now date is before seminar entry ending at. seminar_id = " + seminarId);
            return;
        }

        // エントリー一覧取得
        List<SeminarEntry> entryList = seminarEntryRepository
            .findAllWithTicketsBySeminarId(seminarId);
        for (SeminarEntry entry : entryList) {
            // 開催可否判定
            if (seminar.isEstablish()) {
                // セミナー開催成立の場合
                // 主催者の口座へ振込
                Transfer transfer = transferRepository.transfer(
                    seminar.getAccount().getStripeConnect().getStripeConnectAccountId(),
                    entry.getTicket().getPrice(),
                    entry.getId());

                seminarEntryRepository.updateStripeTransferId(entry.getId(), transfer.getId());

                //応募者へセミナー成立メール送信
                emailSharedService.sendMail(EmailInput.builder()
                    .emailDocumentType(EmailDocumentType.ESTABLISH_APPLICANT)
                    .variableMap(Map.of("account", entry.getAccount(), "seminar", seminar))
                    .recipientEmail(entry.getAccount().getEmail())
                    .locale(LocaleContextHolder.getLocale())
                    .build());

            } else {
                // セミナー開催非成立の場合
                // 応募者へキャッシュバック
                Refund refund = refundRepository.refund(entry.getStripeChargeId());

                seminarEntryRepository.updateStripeRefundId(entry.getId(), refund.getId());

                //応募者へセミナー非成立メール送信
                emailSharedService.sendMail(EmailInput.builder()
                    .emailDocumentType(EmailDocumentType.NOT_ESTABLISH_APPLICANT)
                    .variableMap(Map.of("account", entry.getAccount(), "seminar", seminar))
                    .recipientEmail(entry.getAccount().getEmail())
                    .locale(LocaleContextHolder.getLocale())
                    .build());
            }
        }

        if (seminar.isEstablish()) {
            //主催者へセミナー成立メール送信
            emailSharedService.sendMail(EmailInput.builder()
                .emailDocumentType(EmailDocumentType.ESTABLISH_SPONSOR)
                .variableMap(Map.of("account", seminar.getAccount(),
                    "seminar", seminar,
                    "entryCounts", seminarEntryRepository.countBySeminarIdGroupBySeminarTicketId(seminarId),
                    "web_root", WEB_ROOT))
                .recipientEmail(seminar.getAccount().getEmail())
                .locale(LocaleContextHolder.getLocale())
                .build());

        } else {
            //主催者へセミナー非成立メール送信
            emailSharedService.sendMail(EmailInput.builder()
                .emailDocumentType(EmailDocumentType.NOT_ESTABLISH_SPONSOR)
                .variableMap(Map.of("account", seminar.getAccount(),
                    "seminar", seminar,
                    "entryCounts",
                    seminarEntryRepository.countBySeminarIdGroupBySeminarTicketId(seminarId)))
                .recipientEmail(seminar.getAccount().getEmail())
                .locale(LocaleContextHolder.getLocale())
                .build());
        }

        // update seminar close status
        seminarRepository.updateOpeningStatus(seminarId, OpeningStatus.CLOSED);
    }
}
