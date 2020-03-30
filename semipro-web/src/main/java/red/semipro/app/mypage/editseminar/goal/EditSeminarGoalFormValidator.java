package red.semipro.app.mypage.editseminar.goal;

import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.Validator;
import red.semipro.domain.enums.SeminarType;

/**
 * セミナー目標 - validator
 */
@Component
@RequiredArgsConstructor
public class EditSeminarGoalFormValidator implements Validator {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    private final SmartValidator smartValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return (EditSeminarGoalForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        EditSeminarGoalForm form = (EditSeminarGoalForm) target;

        if (SeminarType.OFFLINE.equals(form.getSeminarType())) {
            // ネストしたBeanの検証の実行
            errors.pushNestedPath("placeForm");
            smartValidator.validate(form.getPlaceForm(), errors);
            // 検証後は、パスを戻す
            errors.popNestedPath();
        }

//        if (PlaceArrangement.MYSELF
//            .equals(PlaceArrangement.getPlaceArrangement(form.getPlaceArrangement()))) {
//            if (Objects.isNull(form.getPrefectureId())) {
//                errors.rejectValue("prefectureId",
//                    "NotNull",
//                    "Incorrect date was entered.");
//            }
//            if (Objects.isNull(form.getCityId())) {
//                errors.rejectValue("cityId",
//                    "NotNull",
//                    "Incorrect date was entered.");
//            }
//            if (Objects.isNull(form.getAddress())) {
//                errors.rejectValue("address",
//                    "NotNull",
//                    "Incorrect date was entered.");
//            }
//            if (Objects.isNull(form.getPlace())) {
//                errors.rejectValue("place",
//                    "NotNull",
//                    "Incorrect date was entered.");
//            }
//        }
//
//        if (errors.hasErrors()) {
//            return;
//        }
//
//        LocalDateTime entryEndingAt = null;
//        try {
//            entryEndingAt = LocalDateTime
//                .parse(form.getEntryEndingAt(), dateTimeFormatter);
//        } catch (Exception e) {
//            errors.rejectValue("entryEndingAt",
//                "IncorrectDate",
//                "Incorrect date was entered.");
//        }
//
//        if (entryEndingAt.isBefore(LocalDateTime.now())) {
//            errors.rejectValue("entryEndingAt",
//                "EntryEndingAtAfterCurrentDate",
//                "please after the current date.");
//        }
//
//        if (errors.hasErrors()) {
//            return;
//        }
//
//        LocalDateTime seminarStartingAt = null;
//        if (Objects.nonNull(form.getSeminarStartingAt())) {
//            try {
//                seminarStartingAt = LocalDateTime
//                    .parse(form.getSeminarStartingAt(), dateTimeFormatter);
//            } catch (Exception e) {
//                errors.rejectValue("seminarStartingAt",
//                    "IncorrectDate",
//                    "Incorrect date was entered.");
//            }
//            if (seminarStartingAt.isBefore(entryEndingAt)) {
//                errors.rejectValue("seminarStartingAt",
//                    "SeminarStartingAtAfterEntryEndingAt",
//                    "please after the entry ending date.");
//            }
//        }
//
//        LocalDateTime seminarEndingAt = null;
//        if (Objects.nonNull(form.getSeminarEndingAt())) {
//            try {
//                seminarEndingAt = LocalDateTime
//                    .parse(form.getSeminarEndingAt(), dateTimeFormatter);
//            } catch (Exception e) {
//                errors.rejectValue("seminarEndingAt",
//                    "IncorrectDate",
//                    "Incorrect date was entered.");
//            }
//            if (Objects.isNull(seminarStartingAt)
//                || seminarEndingAt.isBefore(seminarStartingAt)) {
//                errors.rejectValue("seminarStartingAt",
//                    "SeminarEndingAtAfterSeminarStartingAt",
//                    "please after the seminar starting at.");
//            }
//        }
    }
}
