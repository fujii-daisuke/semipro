package red.semipro.app.mypage.editseminar.identification;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import red.semipro.domain.enums.BusinessType;
import red.semipro.domain.service.account.AccountService;
import red.semipro.share.stripe.StripeConnectHelper;

/**
 * 本人確認 - validator
 */
@Component
@RequiredArgsConstructor
public class EditSeminarIdentificationValidator implements Validator {

    private final SmartValidator smartValidator;
    private final AccountService accountService;
    private final StripeConnectHelper stripeConnectHelper;

    @Override
    public boolean supports(Class<?> clazz) {
        return (EditSeminarIdentificationForm.class).isAssignableFrom(clazz);
    }

    @SneakyThrows
    @Override
    public void validate(Object target, Errors errors) {

        EditSeminarIdentificationForm form = (EditSeminarIdentificationForm) target;

        if (BusinessType.INDIVIDUAL.equals(form.getBusinessType())) {
            // ネストしたBeanの検証の実行
            errors.pushNestedPath("individualForm");
            smartValidator.validate(form.getIndividualForm(), errors);
            // 検証後は、パスを戻す
            errors.popNestedPath();
        } else {
            // ネストしたBeanの検証の実行
            errors.pushNestedPath("companyForm");
            smartValidator.validate(form.getCompanyForm(), errors);
            // 検証後は、パスを戻す
            errors.popNestedPath();
        }
    }
}
