package red.semipro.app.stripe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "stripe")
public class StripeTestController {

    @GetMapping(value = "createToken")
    public ModelAndView input(ModelAndView model) {
        model.setViewName("bancAccountForm");
        return model;
    }

}
