package red.semipro.app.createseminar.overview;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * セミナー概要 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarOverviewHelper {

    private final SeminarService seminarService;
    private final SeminarOverviewService seminarOverviewService;
    private final CreateSeminarOverviewFormConverter createSeminarOverviewFormConverter;

    /**
     * セミナー概要フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー概要フォーム
     */
    public CreateSeminarOverviewForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        SeminarOverview seminarOverview = seminarOverviewService.findOne(seminarId);
        if (Objects.isNull(seminarOverview)) {
            seminarOverview = SeminarOverview.builder()
                .seminarId(seminarId)
                .build();
        }
        return createSeminarOverviewFormConverter.convert(seminarOverview);
    }

    /**
     * セミナー概要を更新します
     *
     * @param form セミナー概要フォーム
     */
    public void save(@Nonnull final CreateSeminarOverviewForm form, @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(seminarService
            .findOneBy(form.getSeminarId(), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        SeminarOverview seminarOverview = createSeminarOverviewFormConverter.convert(form);
        seminarOverviewService.update(seminarOverview);
    }
}
