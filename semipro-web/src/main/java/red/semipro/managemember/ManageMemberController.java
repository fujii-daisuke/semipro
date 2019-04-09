package red.semipro.managemember;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import red.semipro.common.Crypto;
import red.semipro.common.mail.MailService;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Member;
import red.semipro.domain.service.member.MemberService;

@Controller
@RequestMapping(value = "members")
@TransactionTokenCheck(value = "members")
public class ManageMemberController {
    
    @Autowired
    MemberService memberService;
    @Autowired
    MailService mailService;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    MessageSource messageSource;
    
    @Autowired
    MemberFormValidator memberFormValidator;
    
    @InitBinder("memberForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberFormValidator);
    }
    
    @ModelAttribute("memberForm")
    public MemberForm setupMemberForm() {
        return new MemberForm();
    }
    
    @GetMapping
    public ModelAndView clearSessionAtStart(SessionStatus sesseionStatus, ModelAndView model) {
        sesseionStatus.setComplete();
        model.setViewName("forward:/members/create");
        return model;
    }
    
    @GetMapping(value = "create")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(ModelAndView model) {
        model.setViewName("managemember/createForm");
        return model;
    }
    
    @PostMapping(value = "create")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView register(@Validated MemberForm memberForm, 
            BindingResult result,
            ModelAndView model) throws Exception {
        
        if (result.hasErrors()) {
            model.setViewName("managemember/createForm");
            return model;
        }
        
        Member member = Member.builder()
            .email(memberForm.getEmail())
            .username(memberForm.getUsername())
            .password(memberForm.getPassword())
            .registerStatus(RegisterStatus.PRE)
            .build();
        
        memberService.register(member);
        sendMail(member);
        
        model.setViewName("redirect:/members/create?complete");
        return model;
    }
    
    @GetMapping(value = "create", params = "complete")
    public ModelAndView createComplete(ModelAndView model) {
        model.setViewName("managemember/createComplete");
        return model;
    }
    
    private void sendMail(Member member) throws Exception {
        Context ctx = new Context(Locale.getDefault());
        Crypto crypto = new Crypto();
        ctx.setVariable("member", member);
        ctx.setVariable("registerKey", crypto.encrypto(member.getId().toString()));
        String body = templateEngine.process("mail/registrationmember.txt", ctx);
        mailService.sendMail("d.ziifuu@gmail.com", member.getEmail(), "【セミプロ】会員登録のお知らせ", body);
    }
    
}
