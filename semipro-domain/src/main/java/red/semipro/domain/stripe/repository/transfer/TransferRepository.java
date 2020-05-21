package red.semipro.domain.stripe.repository.transfer;

import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import javax.annotation.Nonnull;

public interface TransferRepository {

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
        @Nonnull final Long seminarEntryId) throws StripeException;
}
