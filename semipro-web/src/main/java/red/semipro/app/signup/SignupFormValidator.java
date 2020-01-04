package red.semipro.app.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import red.semipro.domain.service.account.AccountService;

@Component
public class SignupFormValidator implements Validator {

    @Autowired
    AccountService accountService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return (SignupForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        SignupForm form = (SignupForm)target;
        
        if (accountService.isExistsEmail(form.getEmail())) {
            errors.rejectValue("email", "AalreadyExists.email");
        }
        
        if (accountService.isExistsUsername(form.getUsername())) {
            errors.rejectValue("userame", "AalreadyExists.username");
        }
    }
}
