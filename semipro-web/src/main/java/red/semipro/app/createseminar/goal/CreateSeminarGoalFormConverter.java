package red.semipro.app.createseminar.goal;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.PlaceArrangement;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.City;
import red.semipro.domain.model.Prefecture;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.seminar.SeminarPlace;

/**
 * セミナー目標フォーム - converter
 */
@Component
public class CreateSeminarGoalFormConverter {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    /**
     * セミナー目標フォームにコンバートします
     *
     * @param seminar      セミナー
     * @param seminarPlace 　セミナー開催地
     * @return セミナー目標フォーム
     */
    public CreateSeminarGoalForm convert(@Nonnull final Seminar seminar,
        @Nonnull final SeminarPlace seminarPlace) {
        return CreateSeminarGoalForm.builder()
            .seminarId(seminar.getId())
            .seminarType(seminar.getSeminarType())
            .minimumNumber(seminar.getMinimumNumber())
            .capacity(seminar.getCapacity())
            .entryEndingAt(seminar.getEntryEndingAt())
            .successCondition(seminar.getSuccessCondition())
            .seminarStartingAt(seminar.getSeminarStartingAt())
            .seminarEndingAt(seminar.getSeminarEndingAt())
            .placeForm(convert(seminarPlace))
            .build();
    }

    /**
     * セミナー開催地フォームにコンバートします
     *
     * @param seminarPlace セミナー開催地
     * @return セミナー開催地フォーム
     */
    public CreateSeminarPlaceForm convert(@NotNull SeminarPlace seminarPlace) {
        return CreateSeminarPlaceForm.builder()
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
    public Seminar convert(@Nonnull final CreateSeminarGoalForm form,
        @Nonnull final Long accountId) {
        return Seminar.builder()
            .id(form.getSeminarId())
            .seminarType(form.getSeminarType())
            .minimumNumber(form.getMinimumNumber())
            .capacity(form.getCapacity())
            .entryEndingAt(form.getEntryEndingAt())
            .successCondition(form.getSuccessCondition())
            .seminarStartingAt(form.getSeminarStartingAt())
            .seminarEndingAt(form.getSeminarEndingAt())
            .account(Account.builder().id(accountId).build())
            .build();
    }

    /**
     * セミナー開催地モデルにコンバートします
     *
     * @param form セミナー目標フォーム
     * @return セミナー開催地
     */
    public SeminarPlace convert(@NotNull final Long seminarId,
        @Nonnull final CreateSeminarPlaceForm form) {
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
