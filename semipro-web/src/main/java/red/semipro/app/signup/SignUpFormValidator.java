package red.semipro.app.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import red.semipro.domain.service.account.AccountService;

/**
 * 会員登録 - validator
 */
@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountService accountService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return (SignUpForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        SignUpForm form = (SignUpForm)target;
        
        if (accountService.isExistsEmail(form.getEmail(), null)) {
            errors.rejectValue("email", "AalreadyExists.email");
        }
        
        if (accountService.isExistsUsername(form.getUsername(), null)) {
            errors.rejectValue("userame", "AalreadyExists.username");
        }
    }
}
