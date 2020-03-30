package red.semipro.domain.service.stripe;

import java.util.List;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.stripe.StripeCustomerCard;
import red.semipro.domain.repository.stripe.StripeCustomerCardRepository;

/**
 * Stripe顧客カード関連 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StripeCustomerCardService {

    private final StripeCustomerCardRepository stripeCustomerCardRepository;

    /**
     * Stripe顧客カード関連を登録します
     *
     * @param stripeCustomerCard Stripe顧客カード関連
     * @return StripeCustomerCard Stripe顧客カード関連
     */
    public StripeCustomerCard insert(@Nonnull final StripeCustomerCard stripeCustomerCard) {
        stripeCustomerCardRepository.insert(stripeCustomerCard);
        return stripeCustomerCard;
    }

    /**
     * アカウントIDからアカウントStripeコネクト関連を取得します
     *
     * @param stripeCustomerId Stripe顧客ID
     * @return Stripe顧客カード関連リスト
     */
    public List<StripeCustomerCard> findAll(@Nonnull final String stripeCustomerId) {
        return stripeCustomerCardRepository.findAll(stripeCustomerId);
    }
}
