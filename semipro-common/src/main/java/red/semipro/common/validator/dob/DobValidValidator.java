package red.semipro.common.validator.dob;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class DobValidValidator implements ConstraintValidator<DobValid, Object> {

    private String field1;
    private String field2;
    private String field3;
    private String message;

    public void initialize(DobValid constraintAnnotation) {
        // 下記のisValidで使うので、ここでメンバ変数に項目名を入れておいてください。
        field1 = "dobYear";
        field2 = "dobMonth";
        field3 = "dobDay";
        // DobValidクラスのmessage()です。isValidで使用します。
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object field1Value = beanWrapper.getPropertyValue(field1);
        Object field2Value = beanWrapper.getPropertyValue(field2);
        Object field3Value = beanWrapper.getPropertyValue(field3);

        if (Objects.isNull(field1Value) || Objects.isNull(field2Value) || Objects
            .isNull(field3Value)) {
            return true;
        }

        final LocalDate dob;
        try {
            dob = LocalDate.of(Integer.parseInt(String.valueOf(field1Value)),
                Integer.parseInt(String.valueOf(field2Value)),
                Integer.parseInt(String.valueOf(field3Value)));
        } catch (Exception e) {
            addError(context);
            return false;
        }

        if (dob.isAfter(LocalDate.now())) {
            addError(context);
            return false;
        }

        return true;
    }

    private void addError(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        // このようにmessageの設定を入れないと、エラー内容が出力されません。
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(field1)
            // field1の箇所にエラー内容が出力されるようにしています。
            .addConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(field2)
            .addConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(field3)
            .addConstraintViolation();
    }
}
