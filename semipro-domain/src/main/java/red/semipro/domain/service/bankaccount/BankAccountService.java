package red.semipro.domain.service.bankaccount;

import com.stripe.exception.StripeException;
import com.stripe.param.BankAccountUpdateOnAccountParams;
import com.stripe.param.BankAccountUpdateOnAccountParams.AccountHolderType;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeConnect;
import red.semipro.domain.model.account.StripeBankAccount;
import red.semipro.domain.repository.account.AccountStripeConnectRepository;
import red.semipro.domain.repository.account.StripeBankAccountRepository;
import red.semipro.domain.stripe.repository.bankaccount.BankAccountRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountService {

    private final AccountStripeConnectRepository accountStripeConnectRepository;
    private final StripeBankAccountRepository stripeBankAccountRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountConverter bankAccountConverter;

    public BankAccount setup(@Nonnull final Long accountId)
        throws StripeException {

        final AccountStripeConnect accountStripeConnect =
            accountStripeConnectRepository.findOne(accountId);

        if (Objects.isNull(accountStripeConnect)) {
            throw new IllegalStateException("stripe connect account id is null.");
        }

        final StripeBankAccount stripeBankAccount = stripeBankAccountRepository
            .findByStripeConnectAccountId(accountStripeConnect.getStripeConnectAccountId());

        if (Objects.isNull(stripeBankAccount)) {
            return new BankAccount();
        }

        return bankAccountConverter.convert(
            bankAccountRepository.retrieve(
                stripeBankAccount.getStripeConnectAccountId()
                , stripeBankAccount.getBankAccountId()));
    }

    public BankAccount create(@Nonnull final Long accountId, @Nonnull final String token)
        throws StripeException {

        final AccountStripeConnect accountStripeConnect =
            accountStripeConnectRepository.findOne(accountId);

        if (Objects.isNull(accountStripeConnect)) {
            throw new IllegalStateException("stripe connect account id is null.");
        }

        final StripeBankAccount stripeBankAccount = stripeBankAccountRepository
            .findByStripeConnectAccountId(accountStripeConnect.getStripeConnectAccountId());

        if (Objects.nonNull(stripeBankAccount)) {
            throw new IllegalStateException("stripe bank account already exists.");
        }

        com.stripe.model.BankAccount bankAccount = bankAccountRepository
            .create(accountStripeConnect.getStripeConnectAccountId(), token);

        stripeBankAccountRepository.insert(StripeBankAccount.builder()
            .stripeConnectAccountId(accountStripeConnect.getStripeConnectAccountId())
            .bankAccountId(bankAccount.getId())
            .build());

        return bankAccountConverter.convert(bankAccount);
    }

    public BankAccount update(@Nonnull final Long accountId, @Nonnull final BankAccount bankAccount)
        throws StripeException {

        final AccountStripeConnect accountStripeConnect =
            accountStripeConnectRepository.findOne(accountId);

        if (Objects.isNull(accountStripeConnect)) {
            throw new IllegalStateException("stripe connect account id is null.");
        }

        final StripeBankAccount stripeBankAccount = stripeBankAccountRepository
            .findByStripeConnectAccountId(accountStripeConnect.getStripeConnectAccountId());

        if (Objects.isNull(stripeBankAccount)) {
            throw new IllegalStateException("stripe bank account is null.");
        }

        com.stripe.model.BankAccount ba = bankAccountRepository
            .retrieve(stripeBankAccount.getStripeConnectAccountId(),
                stripeBankAccount.getBankAccountId());

        BankAccountUpdateOnAccountParams param =
            BankAccountUpdateOnAccountParams.builder()
                .setAccountHolderName(bankAccount.getAccountHolderName())
                .setAccountHolderType(bankAccount.getBusinessType().getValue()
                    .equals(AccountHolderType.INDIVIDUAL.getValue()) ? AccountHolderType.INDIVIDUAL
                    : AccountHolderType.COMPANY)
                .build();

        return bankAccountConverter.convert(ba.update(param));
    }

    public void delete(@Nonnull final Long accountId) throws StripeException {

        final AccountStripeConnect accountStripeConnect =
            accountStripeConnectRepository.findOne(accountId);

        if (Objects.isNull(accountStripeConnect)) {
            throw new IllegalStateException("stripe connect account id is null.");
        }

        final StripeBankAccount stripeBankAccount = stripeBankAccountRepository
            .findByStripeConnectAccountId(accountStripeConnect.getStripeConnectAccountId());

        if (Objects.isNull(stripeBankAccount)) {
            throw new IllegalStateException("stripe bank account is null.");
        }

        stripeBankAccountRepository.delete(stripeBankAccount.getId());

        bankAccountRepository.delete(stripeBankAccount.getStripeConnectAccountId(),
            stripeBankAccount.getBankAccountId());

    }
}
