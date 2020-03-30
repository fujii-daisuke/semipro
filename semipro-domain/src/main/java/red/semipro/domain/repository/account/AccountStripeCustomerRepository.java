package red.semipro.domain.repository.account;

import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.account.AccountStripeCustomer;

/**
 * アカウントStripe顧客関連 - repository
 */
@Repository
@Mapper
public interface AccountStripeCustomerRepository {

    AccountStripeCustomer findOne(@NotNull final Long accountId);

    int insert(@NotNull final AccountStripeCustomer accountStripeCustomer);

}
