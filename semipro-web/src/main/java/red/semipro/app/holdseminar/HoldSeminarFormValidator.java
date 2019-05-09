package red.semipro.app.holdseminar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HoldSeminarFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (HoldSeminarForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        
        HoldSeminarForm form = (HoldSeminarForm)target;
        LocalDateTime startingDateTime = null;
        LocalDateTime endingDateTime = null;
        try {
            startingDateTime = LocalDateTime.of(LocalDate.parse(form.getStartingDate()), LocalTime.parse(form.getStartingTime()));
        } catch (Exception e) {
            errors.rejectValue("startingDateTime",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }

        try {
            endingDateTime = LocalDateTime.of(LocalDate.parse(form.getEndingDate()), LocalTime.parse(form.getEndingTime()));
        } catch (Exception e) {
            errors.rejectValue("endingDateTime",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }
        
        if (errors.hasErrors()) {
            return;
        }
        
        if (startingDateTime.isAfter(endingDateTime)) {
            errors.rejectValue("endingDateTime",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }
    }

}
