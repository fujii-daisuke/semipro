package red.semipro.domain.model.seminar;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーオプション - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarOption implements Serializable {

    private static final long serialVersionUID = -794506179041951155L;

    /** セミナーID */
    @NotNull
    private Long seminarId;

    /** 撮影サポート要否 */
    @NotNull
    private Boolean shootingSupport;

    /** 編集サポート要否 */
    @NotNull
    private Boolean shootingEditSupport;

    /** 動画販売要否 */
    @NotNull
    private Boolean movieSalesSupport;
}
