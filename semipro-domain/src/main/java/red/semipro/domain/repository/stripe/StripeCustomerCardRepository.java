package red.semipro.domain.repository.stripe;

import java.util.List;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.stripe.StripeCustomerCard;

/**
 * Stripe顧客カード関連 - repository
 */
@Repository
@Mapper
public interface StripeCustomerCardRepository {

    List<StripeCustomerCard> findAll(@NotNull final String stripeCustomerId);

    int insert(@NotNull final StripeCustomerCard stripeCustomerCard);

    boolean hasCard(@Nonnull @Param("accountId") final Long accountId,
        @Nonnull @Param("cardId") final String cardId);

}
