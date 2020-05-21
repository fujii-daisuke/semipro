package red.semipro.domain.stripe.repository.refund;

import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import javax.annotation.Nonnull;

public interface RefundRepository {

    /**
     * プラットフォームから顧客に返金
     *
     * @param chargeId チャージID
     * @return Refund
     * @throws StripeException Stripe例外
     */
    public Refund refund(@Nonnull final String chargeId) throws StripeException;
}
