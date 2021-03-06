package red.semipro.app.mypage.editseminar.overview;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.aws.service.SeminarImageService;

/**
 * セミナー概要 - converter
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOverviewFormConverter {

    private final SeminarImageService seminarImageService;

    /**
     * セミナー概要フォームにコンバートします
     *
     * @param seminarOverview セミナー概要
     * @return セミナー概要フォーム
     */
    public EditSeminarOverviewForm convert(@NotNull final SeminarOverview seminarOverview) {
        return EditSeminarOverviewForm.builder()
            .seminarId(seminarOverview.getSeminarId())
            .title(seminarOverview.getTitle())
            .summary(seminarOverview.getSummary())
            .lecturerProfile(seminarOverview.getLecturerProfile())
            .mainImageUrl(
                Objects.nonNull(seminarOverview.getMainImageExtension()) ? seminarImageService
                    .getMainImageUrl(seminarOverview.getSeminarId(),
                        seminarOverview.getMainImageExtension()) : null)
            .build();
    }

    /**
     * セミナーコンテンツにコンバートします
     *
     * @param form セミナーコンテンツフォーム
     * @return セミナーコンテンツ
     */
    public SeminarOverview convert(@Nonnull final EditSeminarOverviewForm form) {
        return SeminarOverview.builder()
            .seminarId(form.getSeminarId())
            .title(form.getTitle())
            .summary(form.getSummary())
            .lecturerProfile(form.getLecturerProfile())
            .build();
    }
}
