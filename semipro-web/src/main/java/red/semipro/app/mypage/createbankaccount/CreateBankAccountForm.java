package red.semipro.app.mypage.createbankaccount;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.BusinessType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountForm implements Serializable {

    private static final long serialVersionUID = 4336690898407972111L;

    private BusinessType businessType;

    private String bankCode;

    private String branchCode;

    private String accountNumber;

    private String last4;

    private String accountHolderName;

    public boolean isEdit() {
        return Objects.nonNull(bankCode);
    }
}
