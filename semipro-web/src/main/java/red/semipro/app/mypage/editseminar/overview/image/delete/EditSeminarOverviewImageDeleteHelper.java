package red.semipro.app.mypage.editseminar.overview.image.delete;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.SeminarOverview;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * メイン画像削除 - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOverviewImageDeleteHelper {

    private final SeminarOverviewService seminarOverviewService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * メイン画像を削除します
     *
     * @param seminarId セミナーID
     * @param account   アカウント
     */
    public void delete(@Nonnull final Long seminarId, @NotNull final Account account) {

        SeminarOverview seminarOverview = seminarOverviewService
            .findOneEditable(seminarId, account.getId());

        seminarImageHelper.delete(seminarImageHelper
            .createMainImagePath(seminarOverview.getSeminarId(),
                seminarOverview.getMainImageExtension()));

        seminarOverviewService
            .saveMainImageExtension(seminarId, account.getId(), null);
    }
}
