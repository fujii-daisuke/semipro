package red.semipro.app.createseminar.contents;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarContents;

/**
 * セミナーコンテンツ - converter
 */
@Component
public class CreateSeminarContentsFormConverter {

    /**
     * セミナーコンテンツフォームにコンバートします
     *
     * @param seminarContents セミナーコンテンツ
     * @return セミナーコンテンツフォーム
     */
    public CreateSeminarContentsForm convert(@NotNull final SeminarContents seminarContents) {
        return CreateSeminarContentsForm.builder()
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
    public SeminarContents convert(@Nonnull final CreateSeminarContentsForm form) {
        return SeminarContents.builder()
            .seminarId(form.getSeminarId())
            .contents(form.getContents())
            .build();
    }

}
