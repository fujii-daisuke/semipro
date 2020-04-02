package red.semipro.domain.helper.stripe.transfer;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeTransferHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * プラットフォームからコネクトアカウントに送金
     *
     * @param destinationConnectAccountId 送信先コネクトアカウントID
     * @param amount                      金額
     * @param seminarEntryId              セミナーエントリーID
     * @return Transfer
     * @throws StripeException Stripe例外
     */
    public Transfer transfer(@Nonnull final String destinationConnectAccountId,
        @Nonnull final Long amount,
        @Nonnull final Long seminarEntryId) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        TransferCreateParams params = TransferCreateParams.builder()
            .setAmount(amount)
            .setCurrency("jpy")
            .setDestination(destinationConnectAccountId)
            .setTransferGroup(seminarEntryId.toString())
            .build();

        return Transfer.create(params);

    }
}