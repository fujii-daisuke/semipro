package red.semipro.app.createseminar.overview;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * セミナー概要 - converter
 */
@Component
@RequiredArgsConstructor
public class CreateSeminarOverviewFormConverter {

    private final SeminarImageHelper seminarImageHelper;

    /**
     * セミナー概要フォームにコンバートします
     *
     * @param seminarOverview セミナー概要
     * @return セミナー概要フォーム
     */
    public CreateSeminarOverviewForm convert(@NotNull final SeminarOverview seminarOverview) {
        return CreateSeminarOverviewForm.builder()
            .seminarId(seminarOverview.getSeminarId())
            .title(seminarOverview.getTitle())
            .summary(seminarOverview.getSummary())
            .lecturerProfile(seminarOverview.getLecturerProfile())
            .mainImageUrl(
                Objects.nonNull(seminarOverview.getMainImageExtension()) ? seminarImageHelper
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
    public SeminarOverview convert(@Nonnull final CreateSeminarOverviewForm form) {
        return SeminarOverview.builder()
            .seminarId(form.getSeminarId())
            .title(form.getTitle())
            .summary(form.getSummary())
            .lecturerProfile(form.getLecturerProfile())
            .build();
    }
}
