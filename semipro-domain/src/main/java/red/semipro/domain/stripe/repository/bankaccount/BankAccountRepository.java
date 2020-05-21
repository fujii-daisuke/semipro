package red.semipro.domain.stripe.repository.bankaccount;

import com.stripe.exception.StripeException;
import com.stripe.model.BankAccount;
import com.stripe.param.BankAccountUpdateOnAccountParams;
import javax.annotation.Nonnull;

public interface BankAccountRepository {

    public BankAccount create(@Nonnull final String connectAccountId, @Nonnull final String token)
        throws StripeException;

    public BankAccount retrieve(@Nonnull final String connectAccountId,
        @Nonnull final String bankAccountId) throws StripeException;

    public BankAccount update(@Nonnull String connectAccountId,
        @Nonnull String bankAccountId,
        @Nonnull BankAccountUpdateOnAccountParams params) throws StripeException;

    public BankAccount delete(@Nonnull String connectAccountId,
        @Nonnull String bankAccountId) throws StripeException;

}
