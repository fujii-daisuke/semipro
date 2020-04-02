package red.semipro.domain.helper.stripe.charge;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeChargeHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * 顧客からプラットフォームにチャージ
     *
     * @param customerId     顧客ID
     * @param cardId         カードID
     * @param amount         金額
     * @param seminarEntryId セミナーエントリーID
     * @return Charge
     * @throws StripeException Stripe例外
     */
    public Charge charge(@Nonnull final String customerId,
        @Nonnull final String cardId,
        @Nonnull final Long amount,
        @Nonnull final Long seminarEntryId) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        ChargeCreateParams chargeCreateParams = new ChargeCreateParams.Builder()
            .setAmount(amount)
            .setCurrency("JPY")
            .setSource(cardId)
            .setTransferGroup(seminarEntryId.toString())
            .setCustomer(customerId)
            .build();

        return Charge.create(chargeCreateParams);

    }
}
