package red.semipro.app.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import red.semipro.domain.service.member.MemberService;

@Component
public class SignupFormValidator implements Validator {

    @Autowired
    MemberService memberService;
    
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
        
        if (memberService.isExistsEmail(form.getEmail())) {
            errors.rejectValue("email", "AalreadyExists.email");
        }
        
        if (memberService.isExistsUsername(form.getUsername())) {
            errors.rejectValue("userame", "AalreadyExists.username");
        }
    }
}
