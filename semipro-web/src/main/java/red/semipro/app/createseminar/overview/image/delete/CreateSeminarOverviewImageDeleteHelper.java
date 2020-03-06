package red.semipro.app.createseminar.overview.image.delete;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * メイン画像削除 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarOverviewImageDeleteHelper {

    private final SeminarService seminarService;
    private final SeminarOverviewService seminarOverviewService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * メイン画像を削除します
     *
     * @param seminarId セミナーID
     * @param account   アカウント
     * @throws IllegalAccessException セミナーにアクセスできない場合の例外
     */
    public void delete(@Nonnull final Long seminarId, @NotNull final Account account)
        throws IllegalAccessException {

        if (Objects.isNull(
            seminarService.findOneBy(seminarId, account.getId(),
                OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        SeminarOverview seminarOverview = seminarOverviewService.findOne(seminarId);
        if (Objects.isNull(seminarOverview) || Objects
            .isNull(seminarOverview.getMainImageExtension())) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        seminarImageHelper.delete(seminarImageHelper
            .createMainImagePath(seminarOverview.getSeminarId(),
                seminarOverview.getMainImageExtension()));
        seminarOverviewService.updateMainImageExtension(seminarId, null);
    }
}
