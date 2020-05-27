package red.semipro.domain.service.approve;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApproveService {

    private final SeminarSharedService seminarSharedService;
    private final EmailSharedService emailSharedService;

    /**
     * セミナーを承認します
     *
     * @param seminarId セミナーID
     */
    public void approve(Long seminarId) {

        final Seminar seminar = seminarSharedService.findOneWithDetailsForUpdate(
            SeminarCriteria.builder()
                .id(seminarId)
                .openingStatus(OpeningStatus.STRIPE_REGISTERED)
                .build());

        seminarSharedService.save(seminarId, OpeningStatus.OPENING);

        // セミナー主催者へメール送信
        emailSharedService.sendMail(EmailInput.builder()
            .emailDocumentType(EmailDocumentType.RECRUIT)
            .variableMap(Map.of("account", seminar.getAccount(), "seminar", seminar))
            .recipientEmail(seminar.getAccount().getEmail())
            .locale(LocaleContextHolder.getLocale())
            .build());

    }

}
