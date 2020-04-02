package red.semipro.domain.helper.stripe.refund;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeRefundHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * プラットフォームから顧客に返金
     *
     * @param chargeId チャージID
     * @return Refund
     * @throws StripeException Stripe例外
     */
    public Refund refund(@Nonnull final String chargeId) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        RefundCreateParams params = RefundCreateParams.builder()
            .setCharge(chargeId)
            .build();

        return Refund.create(params);

    }

}
