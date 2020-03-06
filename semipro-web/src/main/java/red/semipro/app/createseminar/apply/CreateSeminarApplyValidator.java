package red.semipro.app.createseminar.apply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import red.semipro.domain.model.seminar.SeminarDetail;

@Component
@RequiredArgsConstructor
public class CreateSeminarApplyValidator implements Validator {

    private final SmartValidator smartValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return (SeminarDetail.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SeminarDetail seminarDetail = (SeminarDetail) target;
        smartValidator.validate(seminarDetail, errors);

        if (CollectionUtils.isEmpty(seminarDetail.getSeminarTicketList())) {
                errors.rejectValue("seminarTicketList",
                    "empty",
                    "ticket is empty.");
        }
    }
}
