package red.semipro.app.mypage.editseminar.option;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーサポート - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditSeminarOptionForm implements Serializable {

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
