package red.semipro.app.holdseminar;

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
        
    }

}
