package red.semipro.domain.service.bankaccount;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.BusinessType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 8926121593134992303L;

    private String country = "JP";

    private String currency = "jpy";

    private BusinessType businessType;

    private String bankCode;

    private String branchCode;

    private String accountNumber;

    private String last4;

    private String accountHolderName;

}
