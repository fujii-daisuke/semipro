package red.semipro.app.mypage.editseminar.overview.image.delete;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * メイン画像削除 - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOverviewImageDeleteHelper {

    private final SeminarSharedService seminarSharedService;
    private final SeminarOverviewService seminarOverviewService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * メイン画像を削除します
     *
     * @param seminarId セミナーID
     * @param account   アカウント
     */
    public void delete(@Nonnull final Long seminarId, @NotNull final Account account) {

        final Seminar seminar =
            seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
                .id(seminarId)
                .openingStatus(OpeningStatus.DRAFT)
                .accountId(account.getId())
                .build());

        seminarImageHelper.delete(seminarImageHelper
            .createMainImagePath(seminar.getId(),
                seminar.getOverview().getMainImageExtension()));

        seminarOverviewService
            .saveMainImageExtension(seminarId, account, null);
    }
}
