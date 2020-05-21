package red.semipro.domain.stripe.repository.customer;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import javax.annotation.Nonnull;

public interface CustomerRepository {

    /**
     * Stripe顧客を登録します
     *
     * @param semiproAccountId セミプロアカウントID
     */
    public Customer create(@Nonnull final Long semiproAccountId) throws StripeException;

    public Customer retrieve(String stripeCustomerId) throws StripeException;

}
