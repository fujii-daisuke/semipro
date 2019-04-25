package red.semipro.app.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login")
public class LoginController {

    @ModelAttribute("loginForm")
    public LoginForm LoginForm() {
        return new LoginForm();
    }

    @GetMapping
    public ModelAndView input(ModelAndView modelAndView) {
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