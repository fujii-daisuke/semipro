package red.semipro.domain.stripe.repository.payout;

import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import javax.annotation.Nonnull;

public interface PayoutRepository {

    /**
     * プラットフォームから顧客に返金
     *
     * @param stripeConnectAccountId 子アカウントID
     * @return Payout
     * @throws StripeException Stripe例外
     */
    public Payout payout(@Nonnull final String stripeConnectAccountId, @Nonnull final Long amount)
        throws StripeException;
}
