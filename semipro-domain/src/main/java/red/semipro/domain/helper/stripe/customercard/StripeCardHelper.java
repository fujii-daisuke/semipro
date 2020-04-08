package red.semipro.domain.helper.stripe.customercard;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSourceCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import red.semipro.domain.helper.stripe.customer.StripeCustomerHelper;

/**
 * Stripe カード - helper
 */
@Component
@RequiredArgsConstructor
public class StripeCardHelper {

    private final StripeCustomerHelper stripeCustomerHelper;

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    public List<Card> list(@NotNull final String stripeCustomerId) throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        Customer customer = Customer.retrieve(stripeCustomerId);
        Map<String, Object> params = new HashMap<>();
        params.put("object", "card");
        params.put("limit", 3);

        PaymentSourceCollection paymentSourceCollection = customer.getSources().list(params);
        if (paymentSourceCollection.getData().size() == 0) {
            return List.of();
        }

        return paymentSourceCollection.getData().stream()
            .map(p -> (Card) p)
            .collect(Collectors.toList());
    }

    /**
     * 顧客のカード情報登録を登録します
     * <p>
     * sourceにはStrip.jsで作成したカード情報のトークンを設定
     */
    public Card create(@Nonnull final String stripeCustomerId, @Nonnull final String token)
        throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        Customer customer = stripeCustomerHelper.retrieve(stripeCustomerId);

        Map<String, Object> params = new HashMap<>();
        params.put("source", token);

        return (Card) customer.getSources().create(params);

    }

    public Card retrieve(@Nonnull final String stripeCustomerId, @Nonnull final String cardId)
        throws StripeException {

        Stripe.apiKey = STRIPE_API_KEY;

        Customer customer =
            Customer.retrieve(stripeCustomerId);

        return (Card) customer.getSources().retrieve(cardId);
    }

}
