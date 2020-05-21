package red.semipro.domain.stripe.repository.connect;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountUpdateParams;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

public interface ConnectRepository {

    /**
     * Stripeコネクトアカウントを登録します
     *
     * @param accountCreateParams AccountCreateParams
     * @return Stripeコネクトアカウント
     */
    public Account create(@Nonnull final AccountCreateParams accountCreateParams) throws StripeException;

    /**
     * アカウント情報を取得します
     *
     * @param stripeConnectAccountId StripeコネクトアカウントID
     * @return Stripeアカウント
     * @throws StripeException Stripe通信エラー時の例外
     */
    public Account retrieve(@Nonnull final String stripeConnectAccountId) throws StripeException;

    /**
     * Stripeコネクトアカウントを更新します
     *
     * @param stripeConnectAccountId StripeコネクトアカウントID
     * @param accountUpdateParams    AccountUpdateParams
     * @return Stripeコネクトアカウント
     * @throws StripeException Stripeコネクトアカウント更新時エラー
     */
    public Account update(@NotNull final String stripeConnectAccountId,
        @Nonnull final AccountUpdateParams accountUpdateParams) throws StripeException;
}
