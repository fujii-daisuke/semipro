package red.semipro.api.stripe.addcard;

import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.repository.account.AccountStripeCustomerRepository;
import red.semipro.domain.stripe.repository.customer.CustomerRepository;
import red.semipro.domain.stripe.repository.customercard.CardRepository;
import red.semipro.domain.stripe.repository.customercard.CustomerCard;
import red.semipro.domain.stripe.repository.customercard.CustomerCardConverter;

/**
 * Stripe顧客クレジットカード追加 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class StripeAddCardHelper {

    private final CustomerRepository customerRepository;
    private final CardRepository cardRepository;
    private final AccountStripeCustomerRepository accountStripeCustomerRepository;
    private final CustomerCardConverter customerCardConverter;

    public CustomerCard addCard(@Nonnull final Long accountId, @Nonnull final String token)
        throws StripeException {

        AccountStripeCustomer accountStripeCustomer =
            accountStripeCustomerRepository.findOne(accountId);
        Customer customer;
        if (Objects.isNull(accountStripeCustomer)) {
            customer = customerRepository.create(accountId);

            accountStripeCustomerRepository.insert(AccountStripeCustomer.builder()
                .accountId(accountId)
                .stripeCustomerId(customer.getId())
                .build());
        } else {
            try {
                customer = customerRepository.retrieve(accountStripeCustomer.getStripeCustomerId());
            } catch (StripeException e) {
                e.printStackTrace();
                ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
                throw new BusinessException(message);
            }
        }
        Card card = cardRepository.create(customer.getId(), token);
        return customerCardConverter.convert(card);
    }
}
