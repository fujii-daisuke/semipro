package red.semipro.app.mypage.editseminar.goal;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.PlaceArrangement;
import red.semipro.domain.model.address.City;
import red.semipro.domain.model.address.Prefecture;
import red.semipro.domain.model.seminar.SeminarGoal;
import red.semipro.domain.model.seminar.SeminarPlace;

/**
 * セミナー目標フォーム - converter
 */
@Component
public class EditSeminarGoalFormConverter {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    /**
     * セミナー目標フォームにコンバートします
     *
     * @param seminarGoal セミナー目標
     * @return セミナー目標フォーム
     */
    public EditSeminarGoalForm convert(@Nonnull final SeminarGoal seminarGoal) {
        return EditSeminarGoalForm.builder()
            .seminarId(seminarGoal.getSeminarId())
            .seminarType(seminarGoal.getSeminarType())
            .minimumNumber(seminarGoal.getMinimumNumber())
            .entryEndingAt(seminarGoal.getEntryEndingAt())
            .successCondition(seminarGoal.getSuccessCondition())
            .seminarStartingAt(seminarGoal.getSeminarStartingAt())
            .seminarEndingAt(seminarGoal.getSeminarEndingAt())
            .placeForm(convert(seminarGoal.getPlace()))
            .build();
    }

    /**
     * セミナー開催地フォームにコンバートします
     *
     * @param seminarPlace セミナー開催地
     * @return セミナー開催地フォーム
     */
    public EditSeminarPlaceForm convert(@NotNull SeminarPlace seminarPlace) {
        return EditSeminarPlaceForm.builder()
            .placeArrangement(seminarPlace.getPlaceArrangement())
            .prefectureId(Optional.ofNullable(seminarPlace.getPrefecture()).map(Prefecture::getId)
                .orElse(null))
            .cityId(Optional.ofNullable(seminarPlace.getCity()).map(City::getId).orElse(null))
            .address(seminarPlace.getAddress())
            .place(seminarPlace.getPlace())
            .build();
    }

    /**
     * セミナー目標モデルにコンバートします
     *
     * @param form セミナー目標フォーム
     * @return セミナー目標
     */
    public SeminarGoal convert(@Nonnull final EditSeminarGoalForm form) {
        return SeminarGoal.builder()
            .seminarId(form.getSeminarId())
            .seminarType(form.getSeminarType())
            .minimumNumber(form.getMinimumNumber())
            .entryEndingAt(form.getEntryEndingAt())
            .successCondition(form.getSuccessCondition())
            .seminarStartingAt(form.getSeminarStartingAt())
            .seminarEndingAt(form.getSeminarEndingAt())
            .place(convert(form.getSeminarId(), form.getPlaceForm()))
            .build();
    }

    /**
     * セミナー開催地モデルにコンバートします
     *
     * @param form セミナー目標フォーム
     * @return セミナー開催地
     */
    public SeminarPlace convert(@NotNull final Long seminarId,
        @Nonnull final EditSeminarPlaceForm form) {
        return SeminarPlace.builder()
            .seminarId(seminarId)
            .placeArrangement(form.getPlaceArrangement())
            .prefecture(PlaceArrangement.MYSELF
                .equals(form.getPlaceArrangement())
                ? Prefecture.builder()
                .id(form.getPrefectureId()).build() : null)
            .city(PlaceArrangement.MYSELF
                .equals(form.getPlaceArrangement()) ? City
                .builder()
                .id(form.getCityId()).build() : null)
            .address(PlaceArrangement.MYSELF
                .equals(form.getPlaceArrangement()) ? form
                .getAddress() : null)
            .place(PlaceArrangement.MYSELF
                .equals(form.getPlaceArrangement()) ? form
                .getPlace() : null)
            .build();
    }
}
