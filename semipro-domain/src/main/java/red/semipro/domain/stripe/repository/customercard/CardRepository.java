package red.semipro.domain.stripe.repository.customercard;

import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import java.util.List;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

public interface CardRepository {

    public List<Card> list(@NotNull final String stripeCustomerId) throws StripeException;

    /**
     * 顧客のカード情報登録を登録します
     * <p>
     * sourceにはStrip.jsで作成したカード情報のトークンを設定
     */
    public Card create(@Nonnull final String stripeCustomerId, @Nonnull final String token)
        throws StripeException;

    public Card retrieve(@Nonnull final String stripeCustomerId, @Nonnull final String cardId)
        throws StripeException;
}
