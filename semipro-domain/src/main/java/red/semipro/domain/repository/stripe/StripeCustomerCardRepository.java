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

    StripeCustomerCard findByAccountIdAndCardId(
        @Nonnull @Param("accountId") final Long accountId,
        @Nonnull @Param("cardId") final String cardId);

    List<StripeCustomerCard> findAll(@Nonnull final String stripeCustomerId);

    int insert(@NotNull final StripeCustomerCard stripeCustomerCard);

}
