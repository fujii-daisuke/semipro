package red.semipro.app.createseminar.identification;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.common.validator.dob.DobValid;
import red.semipro.domain.enums.Gender;

/**
 * 個人 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DobValid
public class IndividualForm implements Serializable {

    private static final long serialVersionUID = -5715834089873178463L;

    @NotNull
    @Size(max = 10)
    private String lastName;

    @NotNull
    @Size(max = 10)
    private String firstName;

    @NotNull
    @Size(max = 20)
    private String lastNameKana;

    @NotNull
    @Size(max = 20)
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
    @Pattern(regexp = "[0-9]{10,11}")
    private String phone;

}
