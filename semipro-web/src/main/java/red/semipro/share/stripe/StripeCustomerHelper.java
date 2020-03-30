package red.semipro.share.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Stripe 顧客 - helper
 */
@Component
@RequiredArgsConstructor
public class StripeCustomerHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    public Customer retrieve(String stripeCustomerId)
        throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        return Customer.retrieve(stripeCustomerId);
    }

    /**
     * Stripe顧客を登録します
     *
     * @param semiproAccountId セミプロアカウントID
     * @throws StripeException Stripe例外
     */
    public Customer create(@Nonnull final Long semiproAccountId)
        throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        Map<String, Object> params = new HashMap<>();
        params.put(
            "description",
            "Customer for " + semiproAccountId
                + "(semipro account id)"
        );
        return Customer.create(params);
    }
}
