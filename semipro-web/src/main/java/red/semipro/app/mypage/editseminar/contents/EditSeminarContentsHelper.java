package red.semipro.app.mypage.editseminar.contents;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.SeminarContents;
import red.semipro.domain.service.seminar.SeminarContentsService;

/**
 * セミナーコンテンツ - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarContentsHelper {

    private final SeminarContentsService seminarContentsService;
    private final EditSeminarContentsFormConverter editSeminarContentsFormConverter;

    /**
     * セミナーコンテンツフォームをセットアップします
     *
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return セミナーコンテンツフォーム
     */
    public EditSeminarContentsForm setupForm(@Nonnull final Long seminarId,
        @Nonnull final Long accountId) {
        return editSeminarContentsFormConverter.convert(
            seminarContentsService.findOneEditable(seminarId, accountId));
    }

    /**
     * セミナーコンテンツを更新します
     *
     * @param form      セミナーコンテンツフォーム
     * @param accountId アカウントID
     */
    public void save(@Nonnull final EditSeminarContentsForm form, @Nonnull final Long accountId) {
        SeminarContents seminarContents = editSeminarContentsFormConverter.convert(form);
        seminarContentsService.save(seminarContents, accountId);
    }
}
