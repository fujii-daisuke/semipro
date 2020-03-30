package red.semipro.share.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSource;
import com.stripe.model.PaymentSourceCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.domain.common.constants.MessageId;

/**
 * Stripe カード - helper
 */
@Component
@RequiredArgsConstructor
public class StripeCardHelper {

    private final StripeCustomerHelper stripeCustomerHelper;

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    public List<Card> list(@NotNull final String stripeCustomerId) {

        Stripe.apiKey = STRIPE_API_KEY;

        List<PaymentSource> paymentSources = null;
        try {
            Customer customer = Customer.retrieve(stripeCustomerId);
            Map<String, Object> params = new HashMap<>();
            params.put("object", "card");
            params.put("limit", 3);

            PaymentSourceCollection paymentSourceCollection = customer.getSources().list(params);
            if (paymentSourceCollection.getData().size() == 0) {
                return List.of();
            }

            paymentSources = paymentSourceCollection.getData();

        } catch (StripeException e) {
            e.printStackTrace();
            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        return paymentSources.stream()
            .map(p -> (Card) p)
            .collect(Collectors.toList());
    }

    /**
     * 顧客のカード情報登録を登録します
     * <p>
     * sourceにはStrip.jsで作成したカード情報のトークンを設定
     *
     */
    public Card create(@NotNull final String stripeCustomerId, @NotNull final String token) {

        Stripe.apiKey = STRIPE_API_KEY;

        Card card = null;
        try {
            Customer customer = stripeCustomerHelper.retrieve(stripeCustomerId);

            Map<String, Object> params = new HashMap<>();
            params.put("source", token);

            card = (Card) customer.getSources().create(params);

        } catch (StripeException e) {
            e.printStackTrace();
            ResultMessages message = ResultMessages.error().add(MessageId.E_WEB_0500);
            throw new BusinessException(message);
        }

        return card;
    }

}
