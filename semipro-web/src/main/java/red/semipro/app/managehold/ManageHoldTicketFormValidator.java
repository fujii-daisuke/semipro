package red.semipro.app.managehold;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ManageHoldTicketFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (ManageHoldTicketForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        
        ManageHoldTicketForm form = (ManageHoldTicketForm)target;

        LocalDateTime entryStartingDateTime = null;
        LocalDateTime entryEndingDateTime = null;
        try {
            entryStartingDateTime = LocalDateTime.of(LocalDate.parse(form.getEntryStartingDate()), LocalTime.parse(form.getEntryStartingTime()));
        } catch (Exception e) {
            errors.rejectValue("startingDate",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }

        try {
            entryEndingDateTime = LocalDateTime.of(LocalDate.parse(form.getEntryEndingDate()), LocalTime.parse(form.getEntryEndingTime()));
        } catch (Exception e) {
            errors.rejectValue("endingDate",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }
        
        if (errors.hasErrors()) {
            return;
        }
        
        if (entryStartingDateTime.isAfter(entryEndingDateTime)) {
            errors.rejectValue("endingDate",
                    "IncorrectDate.datetime",
                    "Incorrect date was entered.");
        }
    }
    

}
