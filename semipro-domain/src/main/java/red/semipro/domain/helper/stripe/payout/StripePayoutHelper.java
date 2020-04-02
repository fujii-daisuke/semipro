package red.semipro.domain.helper.stripe.payout;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import com.stripe.net.RequestOptions;
import com.stripe.param.PayoutCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripePayoutHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * プラットフォームから顧客に返金
     *
     * @param stripeConnectAccountId 子アカウントID
     * @return Payout
     * @throws StripeException Stripe例外
     */
    public Payout payout(@Nonnull final String stripeConnectAccountId, @Nonnull final Long amount)
        throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        PayoutCreateParams params =
            PayoutCreateParams.builder()
                .setAmount(amount)
                .setCurrency("jpy")
                .build();

        RequestOptions requestOptions =
            RequestOptions.builder()
                .setStripeAccount(stripeConnectAccountId)
                .build();

        return Payout.create(params, requestOptions);

    }
}
