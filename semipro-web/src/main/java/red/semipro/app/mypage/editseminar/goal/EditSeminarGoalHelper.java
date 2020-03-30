package red.semipro.app.mypage.editseminar.goal;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.SeminarGoal;
import red.semipro.domain.service.seminar.SeminarGoalService;

/**
 * セミナー目標 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class EditSeminarGoalHelper {

    private final SeminarGoalService seminarGoalService;
    private final EditSeminarGoalFormConverter editSeminarGoalFormConverter;

    /**
     * セミナー目標フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー目標フォーム
     */
    public EditSeminarGoalForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {
        return editSeminarGoalFormConverter.convert(
            seminarGoalService.findOneWithPlaceEditable(seminarId, accountId));
    }

    /**
     * セミナー目標を更新します
     *
     * @param form セミナー目標フォーム
     */
    public void save(@Nonnull final EditSeminarGoalForm form, @Nonnull final Long accountId) {
        SeminarGoal seminarGoal = editSeminarGoalFormConverter.convert(form);
        seminarGoalService.save(seminarGoal, accountId);
    }
}
