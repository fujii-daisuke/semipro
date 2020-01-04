package red.semipro.app.activation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.business.email.EmailDocument;
import red.semipro.business.email.EmailService;
import red.semipro.common.Crypto;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.service.account.AccountService;

@RequestMapping(value = "activation")
@Controller
public class ActivationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailService emailService;
    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;
    
    @GetMapping(value="{activationKey}/register")
    public ModelAndView register(@PathVariable("activationKey") String activationKey,
            ModelAndView model, Locale locale) throws Exception {
        
        Crypto crypto = new Crypto();
        String accountId = crypto.decrypto(activationKey);
        Account account = accountService.findOne(Long.valueOf(accountId));
        if (account == null
                || RegisterStatus.REGULAR.equals(account.getRegisterStatus())
                || accountService.isExists(account)) {
            
            model.setViewName("redirect:/activation/failure");
            return model;
        }
        accountService.activation(account);
        sendMail(account, locale);
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

    private void sendMail(Account account, Locale locale) throws Exception {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("account", account);
        emailService.sendMail(
                EmailDocument.ACTIVATED,
                variableMap, 
                account.getEmail(),
                fromEmail, 
                locale);
    }
}
