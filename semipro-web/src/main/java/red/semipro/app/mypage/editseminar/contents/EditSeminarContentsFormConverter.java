package red.semipro.app.mypage.editseminar.contents;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarContents;

/**
 * セミナーコンテンツ - converter
 */
@Component
public class EditSeminarContentsFormConverter {

    /**
     * セミナーコンテンツフォームにコンバートします
     *
     * @param seminarContents セミナーコンテンツ
     * @return セミナーコンテンツフォーム
     */
    public EditSeminarContentsForm convert(@NotNull final SeminarContents seminarContents) {
        return EditSeminarContentsForm.builder()
            .seminarId(seminarContents.getSeminarId())
            .contents(seminarContents.getContents())
            .build();
    }

    /**
     * セミナーコンテンツにコンバートします
     *
     * @param form セミナーコンテンツフォーム
     * @return セミナーコンテンツ
     */
    public SeminarContents convert(@Nonnull final EditSeminarContentsForm form) {
        return SeminarContents.builder()
            .seminarId(form.getSeminarId())
            .contents(form.getContents())
            .build();
    }

}
