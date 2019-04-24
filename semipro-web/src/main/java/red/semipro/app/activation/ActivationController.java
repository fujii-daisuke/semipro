package red.semipro.app.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.common.Crypto;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Member;
import red.semipro.domain.service.member.MemberService;

@RequestMapping(value = "activation")
@Controller
public class ActivationController {

    @Autowired
    MemberService memberService;
    
    @GetMapping(value="{activationKey}/register")
    public ModelAndView register(@PathVariable("activationKey") String activationKey,
            ModelAndView model) throws Exception {
        
        Crypto crypto = new Crypto();
        String memberId = crypto.decrypto(activationKey);
        Member member = memberService.findOne(Long.valueOf(memberId));
        if (member == null 
                || RegisterStatus.REGULAR.equals(member.getRegisterStatus())
                || memberService.isExists(member)) {
            
            model.setViewName("redirect:/activation/failure");
            return model;
        }
        memberService.activation(member);
        model.setViewName("redirect:/activation/completed");
        return model;
    }
    
    @GetMapping(value = "failure")
    public ModelAndView failure(ModelAndView model) {
        model.setViewName("activation/failure");
        return model;
    }
    
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("activation/completed");
        return model;
    }

}
