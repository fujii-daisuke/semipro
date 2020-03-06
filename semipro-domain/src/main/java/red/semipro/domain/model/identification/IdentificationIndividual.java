package red.semipro.domain.model.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.Gender;

/**
 * 本人確認　個人 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationIndividual implements Serializable {

    @NotNull
    private Long identificationId;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastNameKana;

    @NotNull
    private String firstNameKana;

    @NotNull
    private Long dobYear;

    @NotNull
    private Long dobMonth;

    @NotNull
    private Long dobDay;

    @NotNull
    private Gender gender;

    @NotNull
    private String phone;
}
