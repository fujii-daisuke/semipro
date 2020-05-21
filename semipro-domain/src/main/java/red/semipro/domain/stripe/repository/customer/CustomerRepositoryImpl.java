package red.semipro.domain.stripe.repository.customer;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository{

    @Value("${custom.stripe.secret.key}")
    private String STRIPE_SECRET_KEY;

    /**
     * Stripe顧客を登録します
     *
     * @param semiproAccountId セミプロアカウントID
     */
    public Customer create(@Nonnull final Long semiproAccountId) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_KEY;

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
        Stripe.apiKey = STRIPE_SECRET_KEY;

        return Customer.retrieve(stripeCustomerId);
    }

}
