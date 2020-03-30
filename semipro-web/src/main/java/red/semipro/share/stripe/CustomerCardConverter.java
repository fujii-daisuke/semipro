package red.semipro.share.stripe;

import com.stripe.model.Card;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class CustomerCardConverter {

    public List<CustomerCard> convert(@Nonnull final List<Card> cardList) {
        return cardList.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    public CustomerCard convert(@Nonnull final Card card) {
        return CustomerCard.builder()
            .id(card.getId())
            .brand(card.getBrand())
            .expMonth(card.getExpMonth().toString())
            .expYear(card.getExpYear().toString())
            .last4(card.getLast4())
            .build();
    }
}
