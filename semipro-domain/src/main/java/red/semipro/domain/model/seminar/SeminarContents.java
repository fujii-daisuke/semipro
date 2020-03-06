package red.semipro.domain.model.seminar;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナー内容 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarContents implements Serializable {

    private static final long serialVersionUID = 5620099883982987010L;

    /** セミナーID */
    @NotNull
    private Long seminarId;

    /** 内容 */
    @NotNull
    private String contents;

}
