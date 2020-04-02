package red.semipro.domain.helper.stripe.customer;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeCustomerHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * Stripe顧客を登録します
     *
     * @param semiproAccountId セミプロアカウントID
     */
    public Customer create(@Nonnull final Long semiproAccountId) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        Map<String, Object> params = new HashMap<>();
        params.put(
            "description",
            "Customer for " + semiproAccountId
                + "(semipro account id)"
        );
        return Customer.create(params);
    }

    public Customer retrieve(String stripeCustomerId)
        throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;

        return Customer.retrieve(stripeCustomerId);
    }

}
