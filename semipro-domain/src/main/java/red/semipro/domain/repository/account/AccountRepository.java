package red.semipro.domain.repository.account;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Account;

/**
 * アカウント - repository
 */
@Repository
@Mapper
public interface AccountRepository {

    /**
     * アカウントを取得します
     *
     * @param accountId アカウントID
     * @return アカウント
     */
    Account findOne(@Nonnull final Long accountId);

    /**
     * ユーザー名からアカウントを取得します
     *
     * @param username ユーザー名
     * @return アカウント
     */
    Account findByUsername(@Nonnull final String username,
        @Nullable final RegisterStatus registerStatus);

    /**
     * メールアドレスからアカウントを取得します
     *
     * @param email          メールアドレス
     * @param registerStatus 登録ステータス
     * @return アカウント
     */
    Account findByEmail(@Nonnull final String email, @Nullable final RegisterStatus registerStatus);

    /**
     * アカウントを登録する
     *
     * @param account アカウント
     * @return 登録件数
     */
    int insert(@Nonnull final Account account);

    /**
     * 登録ステータス更新します
     *
     * @param accountId      アカウントID
     * @param registerStatus 登録ステータス
     * @return 更新件数
     */
    int updateRegisterStatus(@Nonnull @Param("accountId") Long accountId,
        @Nonnull @Param("registerStatus") RegisterStatus registerStatus);

    /**
     * StripeAccountIdを更新します
     *
     * @param accountId       アカウントID
     * @param stripeAccountId StripeアカウントID
     * @return 更新件数
     */
    int updateStripeAccountId(@Nonnull @Param("accountId") Long accountId,
        @Nonnull @Param("stripeAccountId") String stripeAccountId);
}
