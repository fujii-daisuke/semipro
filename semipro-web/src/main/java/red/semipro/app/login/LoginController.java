package red.semipro.app.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.business.account.AccountUserDetails;

@Controller
@RequestMapping("login")
public class LoginController {

    @ModelAttribute("loginForm")
    public LoginForm LoginForm() {
        return new LoginForm();
    }

    @GetMapping
    public ModelAndView input(@AuthenticationPrincipal AccountUserDetails userDetails,
            ModelAndView modelAndView) {
        if (userDetails != null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("login/login");
        return modelAndView;
    }
    
    @RequestMapping("failure")
    public ModelAndView failure(ModelAndView modelAndView,
            @ModelAttribute("loginForm") LoginForm loginForm) {
        modelAndView.setViewName("login/login");
        modelAndView.addObject("loginForm", loginForm);
        return modelAndView;
    }

}