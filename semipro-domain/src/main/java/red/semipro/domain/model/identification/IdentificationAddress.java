package red.semipro.domain.model.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本人確認 住所 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationAddress implements Serializable {

    private static final long serialVersionUID = 9197130850448009001L;

    @NotNull
    private Long identificationId;

    @NotNull
    private String postalCode;

    @NotNull
    private String state;

    @NotNull
    private String city;

    @NotNull
    private String town;

    @NotNull
    private String line1;

    private String line2;

    @NotNull
    private String stateKana;

    @NotNull
    private String cityKana;

    @NotNull
    private String townKana;

    @NotNull
    private String line1Kana;

    private String line2Kana;
}
