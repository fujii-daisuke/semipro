package red.semipro.domain.stripe.repository.bankaccount;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.BankAccount;
import com.stripe.param.BankAccountUpdateOnAccountParams;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BankAccountRepositoryImpl implements BankAccountRepository {

    @Value("${custom.stripe.secret.key}")
    private String STRIPE_SECRET_KEY;

    @Override
    public BankAccount create(@Nonnull String connectAccountId, @Nonnull String token)
        throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

        Account account = Account.retrieve(connectAccountId);

        Map<String, Object> params = new HashMap<>();
        params.put(
            "external_account",
            token
        );

        return (BankAccount) account
            .getExternalAccounts()
            .create(params);
    }

    @Override
    public BankAccount retrieve(@Nonnull String connectAccountId,
        @Nonnull String bankAccountId) throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

        Account account = Account.retrieve(connectAccountId);

        return (BankAccount) account
            .getExternalAccounts()
            .retrieve(bankAccountId);
    }

    @Override
    public BankAccount update(@Nonnull String connectAccountId,
        @Nonnull String bankAccountId,
        @Nonnull BankAccountUpdateOnAccountParams params) throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

        Account account = Account.retrieve(connectAccountId);

        BankAccount bankAccount = (BankAccount) account.getExternalAccounts()
            .retrieve(bankAccountId);

        return bankAccount.update(params);

    }

    @Override
    public BankAccount delete(@Nonnull String connectAccountId,
        @Nonnull String bankAccountId) throws StripeException {

        Stripe.apiKey = STRIPE_SECRET_KEY;

        Account account = Account.retrieve(connectAccountId);

        BankAccount bankAccount = (BankAccount) account.getExternalAccounts()
            .retrieve(bankAccountId);

        return bankAccount.delete();

    }

}
