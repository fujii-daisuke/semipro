package red.semipro.domain.service.establish;

import com.stripe.exception.StripeException;
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
import red.semipro.domain.helper.stripe.refund.StripeRefundHelper;
import red.semipro.domain.helper.stripe.transfer.StripeTransferHelper;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.repository.seminar.SeminarEntryRepository;
import red.semipro.domain.repository.seminar.SeminarRepository;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EstablishSeminarService {

    private final SeminarSharedService seminarSharedService;
    private final SeminarRepository seminarRepository;
    private final SeminarEntryRepository seminarEntryRepository;
    private final StripeTransferHelper stripeTransferHelper;
    private final StripeRefundHelper stripeRefundHelper;
    private final EmailSharedService emailSharedService;

    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;

    public void establish(@Nonnull final Long seminarId) throws StripeException {

        log.info("セミナーID: " + seminarId + "の開催可否処理を開始します");

        Seminar seminar = seminarSharedService
            .findOneWithDetailsForUpdate(seminarId, OpeningStatus.OPENING);

        // check date
        if (seminar.getGoal().getEntryEndingAt().isBefore(LocalDate.now())) {
            log.info("seminar entry ending at before now date. seminar_id = " + seminarId);
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
                stripeTransferHelper.transfer(
                    seminar.getAccount().getStripeConnect().getStripeConnectAccountId(),
                    entry.getTicket().getPrice(),
                    entry.getId());

                //応募者へセミナー成立メール送信
                emailSharedService.sendMail(EmailInput.builder()
                    .emailDocumentType(EmailDocumentType.ESTABLISH_APPLICANT)
                    .variableMap(Map.of("account", entry.getAccount(), "seminar", seminar))
                    .recipientEmail(entry.getAccount().getEmail())
                    .fromEmail(fromEmail)
                    .locale(LocaleContextHolder.getLocale())
                    .build());

            } else {
                // セミナー開催非成立の場合
                // 応募者へキャッシュバック
                stripeRefundHelper.refund(entry.getStripeChargeId());

                //応募者へセミナー非成立メール送信
                emailSharedService.sendMail(EmailInput.builder()
                    .emailDocumentType(EmailDocumentType.NOT_ESTABLISH_APPLICANT)
                    .variableMap(Map.of("account", entry.getAccount(), "seminar", seminar))
                    .recipientEmail(entry.getAccount().getEmail())
                    .fromEmail(fromEmail)
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
                    "entryCounts",
                    seminarEntryRepository.countBySeminarIdGroupBySeminarTicketId(seminarId)))
                .recipientEmail(seminar.getAccount().getEmail())
                .fromEmail(fromEmail)
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
                .fromEmail(fromEmail)
                .locale(LocaleContextHolder.getLocale())
                .build());
        }

        // update seminar close status
        seminarRepository.updateOpeningStatus(seminarId, OpeningStatus.CLOSED);
    }
}