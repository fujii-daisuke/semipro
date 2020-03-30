package red.semipro.app.stripe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "stripe")
public class StripeTestController {

    @GetMapping(value = "create/bank-account-token")
    public ModelAndView createBankAccountToken(ModelAndView model) {
        model.setViewName("stripe/bancAccountForm");
        return model;
    }

    @GetMapping(value = "create/credit-card-token")
    public ModelAndView createCreditCardToken(ModelAndView model) {
        model.setViewName("stripe/creditCardForm");
        return model;
    }
}
