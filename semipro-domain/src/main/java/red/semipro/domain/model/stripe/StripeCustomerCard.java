package red.semipro.domain.model.stripe;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stripe顧客カード関連 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StripeCustomerCard implements Serializable {

    private static final long serialVersionUID = 7872598224732196894L;

    private Long id;

    private String stripeCustomerId;

    private String cardId;
}
