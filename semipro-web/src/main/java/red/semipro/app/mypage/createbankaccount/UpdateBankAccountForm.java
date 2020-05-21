package red.semipro.app.mypage.createbankaccount;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.BusinessType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBankAccountForm implements Serializable {

    private static final long serialVersionUID = -8264382539070672772L;

    @NotNull
    private BusinessType businessType;

    @NotNull
    private String accountHolderName;

}
