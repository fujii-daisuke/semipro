package red.semipro.app.createseminar.support;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * セミナーサポート - form
 */
@Data
@Builder
public class CreateSeminarSupportForm implements Serializable {

    private static final long serialVersionUID = 6561761878573587057L;

    @NotNull
    private Long seminarId;

    @NotNull
    private Boolean shootingSupport;

    @NotNull
    private Boolean shootingEditSupport;

    @NotNull
    private Boolean movieSalesSupport;

}
