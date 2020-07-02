package red.semipro.app.entryseminar;

import com.google.common.collect.Lists;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.model.seminar.SeminarTicket;
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

    public BindingResult validate(@Nonnull final EntrySeminarForm form,
        @Nonnull final SeminarTicket ticket,
        @Nonnull final BindingResult result) {

        // チケット料金が0円の場合、支払い方法は必須項目とする
        if (!entryService.validate(form.getSelectedStripeCustomerCardId(), ticket)) {
            result.rejectValue("selectedStripeCustomerCardId",
                "paymentMethod.unselected",
                null,
                "Please select a payment method.");
        }
        return result;
    }

    public List<CustomerCard> findStripeCustomerCardList(@Nonnull final Long accountId)
        throws StripeException {

        AccountStripeCustomer accountStripeCustomer =
            accountStripeCustomerRepository.findOne(accountId);

        if (Objects.isNull(accountStripeCustomer)) {
            return List.of();
        }

        List<Card> cardList = cardRepository.list(accountStripeCustomer.getStripeCustomerId());
        List<CustomerCard> customerCardList = Lists.newArrayList();
        for (Card card : cardList) {
            customerCardList.add(customerCardConverter.convert(card));
        }

        return customerCardList;
    }

}
