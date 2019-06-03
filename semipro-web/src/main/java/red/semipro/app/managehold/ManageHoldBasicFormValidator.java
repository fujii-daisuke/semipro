package red.semipro.app.managehold;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ManageHoldBasicFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (ManageHoldBasicForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        ManageHoldBasicForm form = (ManageHoldBasicForm)target;
        
        if (!form.getPlaceSupported()) {
            if (form.getPrefectureId() == null) {
                errors.rejectValue("prefectureId",
                        "NotNull",
                        "Incorrect date was entered.");
            }
            if (form.getCityId() == null) {
                errors.rejectValue("cityId",
                        "NotNull",
                        "Incorrect date was entered.");
            }
            if (StringUtils.isEmpty(form.getAddress())) {
                errors.rejectValue("address",
                        "NotNull",
                        "Incorrect date was entered.");
            }
            if (StringUtils.isEmpty(form.getPlace())) {
                errors.rejectValue("place",
                        "NotNull",
                        "Incorrect date was entered.");
            }
        }
        
        if (errors.hasErrors()) {
            return;
        }
        
        LocalDateTime startingDateTime = null;
        LocalDateTime endingDateTime = null;
        try {
            startingDateTime = LocalDateTime.of(LocalDate.parse(form.getStartingDate()), LocalTime.parse(form.getStartingTime()));
        } catch (Exception e) {
            errors.rejectValue("startingDate",
                    "IncorrectDate",
                    "Incorrect date was entered.");
        }

        try {
            endingDateTime = LocalDateTime.of(LocalDate.parse(form.getEndingDate()), LocalTime.parse(form.getEndingTime()));
        } catch (Exception e) {
            errors.rejectValue("endingDate",
                    "IncorrectDate",
                    "Incorrect date was entered.");
        }
        
        if (errors.hasErrors()) {
            return;
        }
        
        if (!startingDateTime.isBefore(endingDateTime)) {
            errors.rejectValue("endingDate",
                    "BeforeStartDate",
                    "Incorrect date was entered.");
        }
    }
}
