package red.semipro.domain.stripe.repository.refund;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    @Value("${custom.stripe.secret.key}")
    private String STRIPE_SECRET_KEY;

    /**
     * プラットフォームから顧客に返金
     *
     * @param chargeId チャージID
     * @return Refund
     * @throws StripeException Stripe例外
     */
    public Refund refund(@Nonnull final String chargeId) throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

        RefundCreateParams params = RefundCreateParams.builder()
            .setCharge(chargeId)
            .build();

        return Refund.create(params);

    }

}
