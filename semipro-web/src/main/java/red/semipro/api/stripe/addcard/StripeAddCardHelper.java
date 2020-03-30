package red.semipro.api.stripe.addcard;

import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.service.account.AccountStripeCustomerService;
import red.semipro.share.stripe.CustomerCard;
import red.semipro.share.stripe.CustomerCardConverter;
import red.semipro.share.stripe.StripeCardHelper;
import red.semipro.share.stripe.StripeCustomerHelper;

/**
 * Stripe顧客クレジットカード追加 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class StripeAddCardHelper {

    private final StripeCustomerHelper stripeCustomerHelper;
    private final StripeCardHelper stripeCardHelper;
    private final AccountStripeCustomerService accountStripeCustomerService;
    private final CustomerCardConverter customerCardConverter;

    public CustomerCard addCard(@NotNull final Long accountId, @NotNull final String token)
        throws StripeException {

        AccountStripeCustomer accountStripeCustomer = accountStripeCustomerService
            .findOne(accountId);
        Customer customer;
        if (Objects.isNull(accountStripeCustomer)) {
            customer = stripeCustomerHelper.create(accountId);

            accountStripeCustomerService.insert(AccountStripeCustomer.builder()
                .accountId(accountId)
                .stripeCustomerId(customer.getId())
                .build());
        } else {
            customer = stripeCustomerHelper.retrieve(accountStripeCustomer.getStripeCustomerId());
        }
        Card card = stripeCardHelper.create(customer.getId(), token);
        return customerCardConverter.convert(card);
    }
}
