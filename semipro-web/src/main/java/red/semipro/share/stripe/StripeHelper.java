package red.semipro.share.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountUpdateParams;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Stripe - helper
 */
@Component
@RequiredArgsConstructor
public class StripeHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * アカウント情報を取得します
     *
     * @param stripeAccountId StripeアカウントID
     * @return Stripeアカウント
     * @throws StripeException Stripe通信エラー時の例外
     */
    public Account retrieve(@Nonnull final String stripeAccountId) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        return Account.retrieve(stripeAccountId);
    }

    /**
     * 登記番号入力済みチェック
     * @param stripeAccountId StripeアカウントID
     * @return Stripeアカウント
     * @throws StripeException Stripeアカウント検索時エラー
     */
    public boolean taxIdProvided(@Nullable final String stripeAccountId) throws StripeException {
        if(Objects.isNull(stripeAccountId)) {
            return false;
        }
        Account account = retrieve(stripeAccountId);
        if (Objects.isNull(account)) {
            return false;
        }
        if (account.getCompany().getTaxIdProvided()) {
            return true;
        }
        return false;
    }

    /**
     * Stripeアカウントを登録します
     *
     * @param accountCreateParams AccountCreateParams
     * @return Stripeアカウント
     * @throws StripeException Stripeアカウント登録時エラー
     */
    public Account create(@Nonnull final AccountCreateParams accountCreateParams)
        throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        return Account.create(accountCreateParams);
    }

    /**
     * Stripeアカウントを更新します
     *
     * @param stripeAccountId     StripeアカウントID
     * @param accountUpdateParams AccountUpdateParams
     * @return Stripeアカウント
     * @throws StripeException Stripeアカウント更新時エラー
     */
    public Account update(@NotNull final String stripeAccountId,
        @Nonnull final AccountUpdateParams accountUpdateParams) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        Account account = retrieve(stripeAccountId);
        return account.update(accountUpdateParams);
    }
}
