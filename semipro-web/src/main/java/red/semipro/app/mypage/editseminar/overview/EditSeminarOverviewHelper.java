package red.semipro.app.mypage.editseminar.overview;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.service.seminar.SeminarOverviewService;

/**
 * セミナー概要 - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOverviewHelper {

    private final SeminarOverviewService seminarOverviewService;
    private final EditSeminarOverviewFormConverter editSeminarOverviewFormConverter;

    /**
     * セミナー概要フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー概要フォーム
     */
    public EditSeminarOverviewForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {
        return editSeminarOverviewFormConverter.convert(
            seminarOverviewService.findOneEditable(seminarId, accountId));
    }

    /**
     * セミナー概要を更新します
     *
     * @param form セミナー概要フォーム
     */
    public void save(@Nonnull final EditSeminarOverviewForm form, @Nonnull final Long accountId) {
        seminarOverviewService
            .save(editSeminarOverviewFormConverter.convert(form), accountId);
    }
}
