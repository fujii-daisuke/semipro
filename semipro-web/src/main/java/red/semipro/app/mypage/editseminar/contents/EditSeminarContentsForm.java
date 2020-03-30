package red.semipro.app.mypage.editseminar.contents;

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
public class EditSeminarContentsForm implements Serializable {

    private static final long serialVersionUID = -3006399735074074589L;

    @NotNull
    private Long seminarId;

    @NotNull
    @Size(max=10000)
    private String contents;
}
