package red.semipro.domain.stripe.repository.charge;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javax.annotation.Nonnull;

public interface ChargeRepository {

    public Charge charge(@Nonnull final String customerId,
        @Nonnull final String cardId,
        @Nonnull final Long amount,
        @Nonnull final Long seminarEntryId) throws StripeException;
}
