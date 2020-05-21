package red.semipro.domain.repository.account;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.account.StripeBankAccount;

@Repository
@Mapper
public interface StripeBankAccountRepository {

    public StripeBankAccount findByStripeConnectAccountId(
        @Nonnull @Param("stripeConnectAccountId") final String stripeConnectAccountId);

    public int insert(@Nonnull final StripeBankAccount stripeBankAccount);

    public int delete(@NotNull final Long id);
}
