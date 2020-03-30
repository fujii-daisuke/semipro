package red.semipro.app.mypage.editseminar.option;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.seminar.SeminarOption;
import red.semipro.domain.service.seminar.SeminarOptionService;

/**
 * セミナーサポート - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOptionHelper {

    private final SeminarOptionService seminarOptionService;
    private final EditSeminarOptionFormConverter editSeminarOptionFormConverter;

    /**
     * セミナーサポートフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーサポートフォーム
     */
    public EditSeminarOptionForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {

        return editSeminarOptionFormConverter.convert(
            seminarOptionService.findOneEditable(seminarId, accountId));
    }

    /**
     * セミナーサポートを更新します
     *
     * @param form セミナーサポートフォーム
     */
    public void save(@Nonnull final EditSeminarOptionForm form, @Nonnull final Long accountId) {

        SeminarOption seminarOption = editSeminarOptionFormConverter.convert(form);
        seminarOptionService.save(seminarOption, accountId);
    }
}
