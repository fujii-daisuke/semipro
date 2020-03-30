package red.semipro.app.mypage.editseminar.identification;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import red.semipro.domain.enums.BusinessType;

/**
 * 本人確認 - form
 */
@Data
@Builder
public class EditSeminarIdentificationForm implements Serializable {

    private static final long serialVersionUID = -154773065534593627L;

    @NotNull
    private Long id;

    @NotNull
    private Long seminarId;

    @NotNull
    private BusinessType businessType;

    private String ip;

    private CompanyForm companyForm;

    private IndividualForm individualForm;

    @Valid
    private AddressForm addressForm;

}
