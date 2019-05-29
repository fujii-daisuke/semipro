package red.semipro.app.managehold;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ManageHoldAdvancedFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (ManageHoldAdvancedForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        
        ManageHoldAdvancedForm form = (ManageHoldAdvancedForm)target;
        
    }
}
