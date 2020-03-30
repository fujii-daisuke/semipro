package red.semipro.admin.app.registerstripecustomer;

import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * セミナー検索 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/seminars")
public class RegisterStripeCustomerController {

    private final RegisterStripeCustomerHelper registerStripeCustomerHelper;

    /**
     * Stripe会員登録を行います
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     * @throws StripeException Stripeの例外
     */
    @GetMapping(value = "{seminarId}/register-stripe-customer")
    public ModelAndView registerStripeCustomer(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) throws StripeException {

        registerStripeCustomerHelper.register(seminarId);
        model.setViewName("redirect:/seminars/" + seminarId + "/detail");
        return model;
    }

}
