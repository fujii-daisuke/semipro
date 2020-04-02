package red.semipro.domain.helper.stripe.connect;

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
 * Stripe コネクト - helper
 */
@Component
@RequiredArgsConstructor
public class StripeConnectHelper {

    @Value("${custom.stripe.api.key}")
    private String STRIPE_API_KEY;

    /**
     * Stripeコネクトアカウントを登録します
     *
     * @param accountCreateParams AccountCreateParams
     * @return Stripeコネクトアカウント
     */
    public Account create(@Nonnull final AccountCreateParams accountCreateParams)
        throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        return Account.create(accountCreateParams);
    }

    /**
     * アカウント情報を取得します
     *
     * @param stripeConnectAccountId StripeコネクトアカウントID
     * @return Stripeアカウント
     * @throws StripeException Stripe通信エラー時の例外
     */
    public Account retrieve(@Nonnull final String stripeConnectAccountId) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        return Account.retrieve(stripeConnectAccountId);
    }

    /**
     * 登記番号入力済みチェック
     *
     * @param stripeConnectAccountId StripeコネクトアカウントID
     * @return Stripeアカウント
     * @throws StripeException Stripeアカウント検索時エラー
     */
    public boolean taxIdProvided(@Nullable final String stripeConnectAccountId)
        throws StripeException {
        if (Objects.isNull(stripeConnectAccountId)) {
            return false;
        }
        Account account = retrieve(stripeConnectAccountId);
        if (Objects.isNull(account)) {
            return false;
        }
        if (account.getCompany().getTaxIdProvided()) {
            return true;
        }
        return false;
    }

    /**
     * Stripeコネクトアカウントを更新します
     *
     * @param stripeConnectAccountId StripeコネクトアカウントID
     * @param accountUpdateParams    AccountUpdateParams
     * @return Stripeコネクトアカウント
     * @throws StripeException Stripeコネクトアカウント更新時エラー
     */
    public Account update(@NotNull final String stripeConnectAccountId,
        @Nonnull final AccountUpdateParams accountUpdateParams) throws StripeException {
        Stripe.apiKey = STRIPE_API_KEY;
        Account account = retrieve(stripeConnectAccountId);
        return account.update(accountUpdateParams);
    }
}
