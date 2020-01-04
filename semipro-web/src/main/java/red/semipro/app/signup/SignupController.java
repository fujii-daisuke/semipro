package red.semipro.app.signup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import red.semipro.business.email.EmailDocument;
import red.semipro.business.email.EmailService;
import red.semipro.common.Crypto;
import red.semipro.domain.model.Account;
import red.semipro.domain.service.account.AccountService;

@Controller
@RequestMapping(value = "signup")
@TransactionTokenCheck(value = "signup")
public class SignupController {
    
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SignupFormValidator signupFormValidator;
    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${custom.application.schema}")
    private String schema;
    @Value("${custom.application.domain}")
    private String domain;
    
    @InitBinder("signupForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupFormValidator);
    }
    
    @ModelAttribute("signupForm")
    public SignupForm setupSignupForm() {
        return new SignupForm();
    }
    
    @GetMapping
    public ModelAndView clearSessionAtStart(SessionStatus sesseionStatus, ModelAndView model) {
        sesseionStatus.setComplete();
        model.setViewName("forward:/signup/input");
        return model;
    }
    
    @GetMapping(value = "input")
    @TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
    public ModelAndView input(ModelAndView model) {
        model.setViewName("signup/input");
        return model;
    }
    
    @PostMapping(value = "register")
    @TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
    public ModelAndView register(@Validated SignupForm signupForm, 
            BindingResult result,
            Locale locale,
            ModelAndView model) throws Exception {
        
        if (result.hasErrors()) {
            model.setViewName("signup/input");
            return model;
        }
        
        Account account = accountService.register(new Account(signupForm.getEmail(),signupForm.getUsername(), passwordEncoder.encode(signupForm.getPassword())));
        sendMail(account, locale);
        
        model.setViewName("redirect:/signup/completed");
        return model;
    }
    
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("signup/completed");
        return model;
    }
    
    private void sendMail(Account account, Locale locale) throws Exception {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("schema", schema);
        variableMap.put("domain", domain);
        variableMap.put("account", account);
        variableMap.put("activationKey", (new Crypto()).encrypto(account.getId().toString()));
        emailService.sendMail(
                EmailDocument.SIGNUPED,
                variableMap, 
                account.getEmail(),
                fromEmail, 
                locale);
    }
    
}
