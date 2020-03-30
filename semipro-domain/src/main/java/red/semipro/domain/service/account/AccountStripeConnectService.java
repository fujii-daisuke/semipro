package red.semipro.domain.service.account;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.account.AccountStripeConnect;
import red.semipro.domain.repository.account.AccountStripeConnectRepository;

/**
 * アカウントStripeコネクト関連 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccountStripeConnectService {

    private final AccountStripeConnectRepository accountStripeConnectRepository;

    /**
     * アカウントStripeコネクト関連を登録します
     *
     * @param accountStripeConnect アカウントStripeコネクト関連
     * @return AccountStripeConnect アカウントStripeコネクト関連
     */
    public AccountStripeConnect insert(@Nonnull final AccountStripeConnect accountStripeConnect) {
        accountStripeConnectRepository.insert(accountStripeConnect);
        return accountStripeConnect;
    }

    /**
     * アカウントIDからアカウントStripeコネクト関連を取得します
     *
     * @param accountId アカウントID
     * @return AccountStripeConnect アカウントStripeコネクト関連
     */
    public AccountStripeConnect findOne(@Nonnull final Long accountId) {
        return accountStripeConnectRepository.findOne(accountId);
    }

}
