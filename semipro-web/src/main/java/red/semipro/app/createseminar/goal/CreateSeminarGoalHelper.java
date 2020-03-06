package red.semipro.app.createseminar.goal;

import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarPlace;
import red.semipro.domain.service.seminar.SeminarPlaceService;
import red.semipro.domain.service.seminar.SeminarService;

/**
 * セミナー目標 - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarGoalHelper {

    private final SeminarService seminarService;
    private final SeminarPlaceService seminarPlaceService;
    private final CreateSeminarGoalFormConverter createSeminarGoalFormConverter;

    /**
     * セミナー目標フォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナー目標フォーム
     */
    public CreateSeminarGoalForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) throws IllegalAccessException {
        Seminar seminar = seminarService.findOneBy(seminarId, accountId, OpeningStatus.DRAFT);
        if (Objects.isNull(seminar)) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        SeminarPlace seminarPlace = seminarPlaceService.findOne(seminarId);
        if (Objects.isNull(seminarPlace)) {
            seminarPlace = SeminarPlace.builder()
                .build();
        }
        return createSeminarGoalFormConverter.convert(seminar, seminarPlace);
    }

    /**
     * セミナー目標を更新します
     *
     * @param form セミナー目標フォーム
     */
    public void save(@Nonnull final CreateSeminarGoalForm form, @Nonnull final Long accountId)
        throws IllegalAccessException {
        if (Objects.isNull(
            seminarService.findOneBy(form.getSeminarId(), accountId, OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }
        Seminar seminar = createSeminarGoalFormConverter.convert(form, accountId);
        seminarService.update(seminar);
        if (SeminarType.OFFLINE.equals(seminar.getSeminarType())
            || SeminarType.BOTH.equals(seminar.getSeminarType())) {
            SeminarPlace seminarPlace = createSeminarGoalFormConverter
                .convert(form.getSeminarId(), form.getPlaceForm());
            seminarPlaceService.insertOrUpdate(seminarPlace);
        }
    }
}
