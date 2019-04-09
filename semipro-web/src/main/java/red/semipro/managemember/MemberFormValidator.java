package red.semipro.managemember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import red.semipro.domain.service.member.MemberService;

@Component
public class MemberFormValidator implements Validator {

    @Autowired
    MemberService memberService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return (MemberForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        MemberForm form = (MemberForm)target;
        
        if (memberService.isExistsEmail(form.getEmail())) {
            errors.rejectValue("email", "AalreadyExists.email");
        }
        
        if (memberService.isExistsUsername(form.getUsername())) {
            errors.rejectValue("userame", "AalreadyExists.username");
        }
    }
}