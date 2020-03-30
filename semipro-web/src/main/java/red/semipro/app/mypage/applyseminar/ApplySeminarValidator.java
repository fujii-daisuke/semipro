package red.semipro.app.mypage.applyseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import red.semipro.domain.model.seminar.Seminar;

@Component
@RequiredArgsConstructor
public class ApplySeminarValidator implements Validator {

    private final SmartValidator smartValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return (Seminar.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Seminar seminar = (Seminar) target;
        smartValidator.validate(seminar, errors);

        if (CollectionUtils.isEmpty(seminar.getTicketList())) {
                errors.rejectValue("seminarTicketList",
                    "empty",
                    "ticket is empty.");
        }
    }
}
