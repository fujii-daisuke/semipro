package red.semipro.domain.model.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アカウントStripeコネクト関連 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStripeConnect implements Serializable {

    private static final long serialVersionUID = -5942862625519630783L;

    private Long id;

    private Long accountId;

    private String stripeConnectAccountId;
}
