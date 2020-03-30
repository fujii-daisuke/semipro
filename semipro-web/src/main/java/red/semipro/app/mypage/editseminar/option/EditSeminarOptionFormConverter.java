package red.semipro.app.mypage.editseminar.option;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarOption;

/**
 * セミナーサポート - converter
 */
@Component
public class EditSeminarOptionFormConverter {

    /**
     * セミナーサポートフォームにコンバートします
     *
     * @param seminarOption セミナーサポート
     * @return セミナーサポートフォーム
     */
    public EditSeminarOptionForm convert(@NotNull final SeminarOption seminarOption) {
        return EditSeminarOptionForm.builder()
            .seminarId(seminarOption.getSeminarId())
            .shootingSupport(seminarOption.getShootingSupport())
            .shootingEditSupport(seminarOption.getShootingEditSupport())
            .movieSalesSupport(seminarOption.getMovieSalesSupport())
            .build();
    }

    /**
     * セミナーサポートにコンバートします
     *
     * @param form セミナーサポートフォーム
     * @return セミナーサポート
     */
    public SeminarOption convert(@Nonnull final EditSeminarOptionForm form) {
        return SeminarOption.builder()
            .seminarId(form.getSeminarId())
            .shootingSupport(form.getShootingSupport())
            .shootingEditSupport(form.getShootingEditSupport())
            .movieSalesSupport(form.getMovieSalesSupport())
            .build();
    }
}
