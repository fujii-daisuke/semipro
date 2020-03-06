package red.semipro.app.createseminar.contents;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * セミナーコンテンツ - form
 */
@Data
@Builder
public class CreateSeminarContentsForm implements Serializable {

    private static final long serialVersionUID = -3006399735074074589L;

    @NotNull
    private Long seminarId;

    @NotNull
    @Size(max=10000)
    private String contents;
}
