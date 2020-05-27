package red.semipro.app.mypage.editseminar.overview.image.upload;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * メイン画像アップロード - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarOverviewImageUploadHelper {

    private final SeminarImageHelper seminarImageHelper;
    private final SeminarOverviewService seminarOverviewService;

    /**
     * メイン画像をアップロードします
     *
     * @param form メイン画像アップロードフォーム
     * @return 画像URL
     * @throws IOException ファイルアップロード時の例外
     */
    public String upload(@Nonnull final EditSeminarOverviewImageUploadForm form,
        @NotNull final Account account)
        throws IOException {

        final String extension = FilenameUtils
            .getExtension(form.getImage().getOriginalFilename());

        seminarOverviewService
            .saveMainImageExtension(form.getSeminarId(),
                account,
                extension);

        seminarImageHelper.upload(form.getImage(),
            seminarImageHelper.createMainImagePath(form.getSeminarId(), extension));

        return seminarImageHelper.getMainImageUrl(form.getSeminarId(), extension);
    }
}
