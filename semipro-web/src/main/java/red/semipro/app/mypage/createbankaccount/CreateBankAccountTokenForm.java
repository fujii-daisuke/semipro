package red.semipro.app.mypage.createbankaccount;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountTokenForm implements Serializable {

    private static final long serialVersionUID = 381167842826354574L;

    @NotNull
    private String token;
}
