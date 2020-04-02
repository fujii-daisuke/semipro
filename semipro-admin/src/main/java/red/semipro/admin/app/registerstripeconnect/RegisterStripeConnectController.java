package red.semipro.admin.app.registerstripeconnect;

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
public class RegisterStripeConnectController {

    private final RegisterStripeConnectHelper registerStripeConnectHelper;

    /**
     * Stripe会員登録を行います
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     * @throws StripeException Stripeの例外
     */
    @GetMapping(value = "{seminarId}/register-stripe-connect")
    public ModelAndView registerStripeCustomer(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) throws StripeException {

        registerStripeConnectHelper.register(seminarId);
        model.setViewName("redirect:/seminars/" + seminarId + "/detail");
        return model;
    }

}
