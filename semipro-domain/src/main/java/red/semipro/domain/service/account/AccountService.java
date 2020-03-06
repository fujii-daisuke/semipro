package red.semipro.domain.service.account;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.repository.account.AccountRepository;

/**
 * アカウント - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * メールアドレスからアカウントを取得します
     *
     * @param email          メールアドレス
     * @param registerStatus 登録ステータス
     * @return アカウント
     */
    public Account findByEmail(@Nonnull final String email,
        @Nonnull final RegisterStatus registerStatus) {
        return accountRepository.findByEmail(email, registerStatus);
    }

    /**
     * メールアドレスが登録済みか判定します
     *
     * @param email          メールアドレス
     * @param registerStatus 登録ステータス
     * @return boolean true 存在する / false 存在しない
     */
    public boolean isExistsEmail(@Nonnull final String email,
        @Nullable final RegisterStatus registerStatus) {
        return Objects.nonNull(accountRepository.findByEmail(email, registerStatus));
    }

    /**
     * ユーザー名が登録済みか判定します
     *
     * @param username       ユーザー名
     * @param registerStatus 登録ステータス
     * @return boolean true 存在する / false 存在しない
     */
    public boolean isExistsUsername(@Nonnull final String username,
        @Nullable final RegisterStatus registerStatus) {
        return Objects.nonNull(accountRepository.findByUsername(username, registerStatus));
    }

    /**
     * アカウントを登録します
     *
     * @param account アカウント
     * @return アカウント
     */
    public Account register(@Nonnull final Account account) {
        accountRepository.insert(account);
        return account;
    }

    /**
     * アカウントIDからアカウントを取得します
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    public Account findOne(@Nonnull final Long accountId) {
        return accountRepository.findOne(accountId);
    }

    /**
     * 登録ステータスを更新します
     *
     * @param accountId      アカウントID
     * @param registerStatus 登録ステータス
     */
    public void updateRegisterStatus(@Nonnull final Long accountId,
        @NotNull final RegisterStatus registerStatus) {
        accountRepository.updateRegisterStatus(accountId, registerStatus);
    }

    /**
     * StripeアカウントIDを更新します
     *
     * @param accountId       アカウントID
     * @param stripeAccountId StripeアカウントID
     */
    public void updateStripeAccountId(@Nonnull final Long accountId,
        @NotNull final String stripeAccountId) {
        accountRepository.updateStripeAccountId(accountId, stripeAccountId);
    }

}
