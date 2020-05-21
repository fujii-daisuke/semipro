package red.semipro.domain.stripe.repository.payout;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Payout;
import com.stripe.net.RequestOptions;
import com.stripe.param.PayoutCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayoutRepositoryImpl implements PayoutRepository {

    @Value("${custom.stripe.secret.key}")
    private String STRIPE_SECRET_KEY;

    /**
     * プラットフォームから顧客に返金
     *
     * @param stripeConnectAccountId 子アカウントID
     * @return Payout
     * @throws StripeException Stripe例外
     */
    public Payout payout(@Nonnull final String stripeConnectAccountId, @Nonnull final Long amount)
        throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

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
