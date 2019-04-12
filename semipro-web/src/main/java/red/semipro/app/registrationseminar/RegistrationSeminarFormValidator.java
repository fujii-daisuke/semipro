package red.semipro.app.registrationseminar;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationSeminarFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (RegistrationSeminarForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        
    }

}
