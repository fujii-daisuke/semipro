package red.semipro.app.mypage.entryseminar;

import com.google.common.collect.Lists;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.repository.account.AccountStripeCustomerRepository;
import red.semipro.domain.service.entry.EntryService;
import red.semipro.domain.stripe.repository.customercard.CardRepository;
import red.semipro.domain.stripe.repository.customercard.CustomerCard;
import red.semipro.domain.stripe.repository.customercard.CustomerCardConverter;

/**
 * セミナー予約 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class EntrySeminarHelper {

    private final AccountStripeCustomerRepository accountStripeCustomerRepository;
    private final CardRepository cardRepository;
    private final CustomerCardConverter customerCardConverter;
    private final EntryService entryService;

    public List<CustomerCard> findStripeCustomerCardList(@Nonnull final Long accountId)
        throws StripeException {

        AccountStripeCustomer accountStripeCustomer =
            accountStripeCustomerRepository.findOne(accountId);

        if (Objects.isNull(accountStripeCustomer)) {
            return List.of();
        }

        List<Card> cardList = cardRepository.list(accountStripeCustomer.getStripeCustomerId());
        List<CustomerCard> customerCardList = Lists.newArrayList();
        for (Card card: cardList) {
            customerCardList.add(customerCardConverter.convert(card));
        }

        return customerCardList;
    }

}
