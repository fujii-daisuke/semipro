package red.semipro.domain.service.apply;

import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;
import red.semipro.domain.service.seminar.SeminarSharedService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplySeminarService {

    private final SeminarSharedService seminarSharedService;
    private final ApplySeminarValidator applySeminarValidator;
    private final EmailSharedService emailSharedService;

    @Value("${custom.application.email.operator}")
    private String operatorEmail;

    /**
     * セミナー申請を行います
     *
     * @param seminarId セミナーID
     * @param account アカウント
     */
    public BindingResult apply(@Nonnull final Long seminarId, @Nonnull Account account) {

        Seminar seminar =
            seminarSharedService.findOneWithDetailsByIdAndOpeningStatusAndAccountId(
                seminarId, OpeningStatus.DRAFT, account.getId());

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(seminar,
            "seminarDetail");
        applySeminarValidator.validate(seminar, bindingResult);

        if (!bindingResult.hasErrors()) {
            seminarSharedService.save(seminar.getId(), OpeningStatus.APPLYING);

            //申請者へセミナー申請メール送信
            emailSharedService.sendMail(EmailInput.builder()
                .emailDocumentType(EmailDocumentType.APPLY)
                .variableMap(Map.of("account", account, "seminar", seminar))
                .recipientEmail(account.getEmail())
                .bcc(operatorEmail)
                .locale(LocaleContextHolder.getLocale())
                .build());
        }
        return bindingResult;
    }
}
