package red.semipro.domain.model.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.BusinessType;

/**
 * 本人確認 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Identification implements Serializable {

    private static final long serialVersionUID = -2515193248612969899L;

    @NotNull
    private Long id;

    @NotNull
    private Long seminarId;

    @NotNull
    private BusinessType businessType;

    @NotNull
    private String ip;

}
