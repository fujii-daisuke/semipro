package red.semipro.domain.model.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本人確認 法人 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationCompany implements Serializable {

    private static final long serialVersionUID = -5708612983729052212L;

    @NotNull
    private Long identificationId;

    @NotNull
    private String companyName;

    @NotNull
    private String companyNameKana;

    @NotNull
    private String tax;

    @NotNull
    private String phone;
}
