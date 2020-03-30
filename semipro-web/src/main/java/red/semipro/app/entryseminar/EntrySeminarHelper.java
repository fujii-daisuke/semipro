package red.semipro.app.entryseminar;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.service.account.AccountStripeCustomerService;
import red.semipro.domain.service.entry.EntrySeminarInput;
import red.semipro.domain.service.entry.EntrySeminarService;
import red.semipro.share.stripe.CustomerCard;
import red.semipro.share.stripe.CustomerCardConverter;
import red.semipro.share.stripe.StripeCardHelper;

/**
 * セミナー予約 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class EntrySeminarHelper {

    private final AccountStripeCustomerService accountStripeCustomerService;
    private final StripeCardHelper stripeCardHelper;
    private final CustomerCardConverter customerCardConverter;
    private final EntrySeminarService entrySeminarService;

    public List<CustomerCard> findStripeCustomerCardList(@Nonnull final Long accountId) {

        AccountStripeCustomer accountStripeCustomer =
            accountStripeCustomerService.findOne(accountId);

        return Optional.ofNullable(accountStripeCustomer)
            .map(a -> customerCardConverter.convert(stripeCardHelper.list(a.getStripeCustomerId())))
            .orElse(List.of());
    }

    public void entry(
        @Nonnull final Long seminarId,
        @Nonnull final Long ticketId,
        @Nonnull final Long entryAccountId,
        @Nonnull final String stripeCustomerCardId) {

        EntrySeminarInput entrySeminarInput = EntrySeminarInput.builder()
            .seminarId(seminarId)
            .ticketId(ticketId)
            .entryAccountId(entryAccountId)
            .stripeCustomerCardId(stripeCustomerCardId)
            .build();

        entrySeminarService.entry(entrySeminarInput);

    }
}
