package red.semipro.domain.service.account;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeCustomer;
import red.semipro.domain.repository.account.AccountStripeCustomerRepository;

/**
 * アカウントStripe顧客関連 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccountStripeCustomerService {

    private final AccountStripeCustomerRepository accountStripeCustomerRepository;

    /**
     * アカウントStripe顧客関連を登録します
     *
     * @param accountStripeCustomer アカウントStripe顧客関連
     * @return AccountStripeConnect アカウントStripe顧客関連
     */
    public AccountStripeCustomer insert(
        @Nonnull final AccountStripeCustomer accountStripeCustomer) {
        accountStripeCustomerRepository.insert(accountStripeCustomer);
        return accountStripeCustomer;
    }

    /**
     * アカウントIDからアカウントStripe顧客関連を取得します
     *
     * @param accountId アカウントID
     * @return AccountStripeConnect アカウントStripe顧客関連
     */
    public AccountStripeCustomer findOne(@Nonnull final Long accountId) {
        return accountStripeCustomerRepository.findOne(accountId);
    }

}
