package red.semipro.domain.repository.account;

import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.account.AccountStripeConnect;

/**
 * アカウントStripeコネクト関連 - repository
 */
@Repository
@Mapper
public interface AccountStripeConnectRepository {

    AccountStripeConnect findOne(@NotNull final Long accountId);

    int insert(@NotNull final AccountStripeConnect accountStripeConnect);
}
