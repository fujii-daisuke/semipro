package red.semipro.subscriptionmember;

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

@RequestMapping(value = "members")
@Controller
public class SubscriptionMemberController {

    @Autowired
    MemberService memberService;
    
    @GetMapping(value="{registerKey}/subscription")
    public ModelAndView register(@PathVariable("registerKey") String registerKey,
            ModelAndView model) throws Exception {
        
        Crypto crypto = new Crypto();
        String memberId = crypto.decrypto(registerKey);
        Member member = memberService.findOne(Long.valueOf(memberId));
        if (member == null 
                || RegisterStatus.REGULAR.equals(member.getRegisterStatus())
                || memberService.isExists(member)) {
            
            model.setViewName("redirect:/members/subscription/failure");
            return model;
        }
        memberService.subscription(member);
        model.setViewName("redirect:/members/subscription/completed");
        return model;
    }
    
    @GetMapping(value = "subscription/failure")
    public ModelAndView failure(ModelAndView model) {
        model.setViewName("subscriptionmember/failure");
        return model;
    }
    
    @GetMapping(value = "subscription/completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("subscriptionmember/completed");
        return model;
    }

}
