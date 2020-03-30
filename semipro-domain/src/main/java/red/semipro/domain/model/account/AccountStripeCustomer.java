package red.semipro.domain.model.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アカウントStripe顧客関連 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStripeCustomer implements Serializable {

    private static final long serialVersionUID = -2403053079141290108L;

    private Long id;

    private Long accountId;

    private String stripeCustomerId;

}
