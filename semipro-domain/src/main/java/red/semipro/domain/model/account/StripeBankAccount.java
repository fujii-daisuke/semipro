package red.semipro.domain.model.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StripeBankAccount implements Serializable {

    private static final long serialVersionUID = 7771283531530046717L;

    private Long id;

    private String stripeConnectAccountId;

    private String bankAccountId;
}
