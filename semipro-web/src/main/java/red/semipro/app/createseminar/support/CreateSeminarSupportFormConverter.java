package red.semipro.app.createseminar.support;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarSupport;

/**
 * セミナーサポート - converter
 */
@Component
public class CreateSeminarSupportFormConverter {

    /**
     * セミナーサポートフォームにコンバートします
     *
     * @param seminarSupport セミナーサポート
     * @return セミナーサポートフォーム
     */
    public CreateSeminarSupportForm convert(@NotNull final SeminarSupport seminarSupport) {
        return CreateSeminarSupportForm.builder()
            .seminarId(seminarSupport.getSeminarId())
            .shootingSupport(seminarSupport.getShootingSupport())
            .shootingEditSupport(seminarSupport.getShootingEditSupport())
            .movieSalesSupport(seminarSupport.getMovieSalesSupport())
            .build();
    }

    /**
     * セミナーサポートにコンバートします
     *
     * @param form セミナーサポートフォーム
     * @return セミナーサポート
     */
    public SeminarSupport convert(@Nonnull final CreateSeminarSupportForm form) {
        return SeminarSupport.builder()
            .seminarId(form.getSeminarId())
            .shootingSupport(form.getShootingSupport())
            .shootingEditSupport(form.getShootingEditSupport())
            .movieSalesSupport(form.getMovieSalesSupport())
            .build();
    }
}
