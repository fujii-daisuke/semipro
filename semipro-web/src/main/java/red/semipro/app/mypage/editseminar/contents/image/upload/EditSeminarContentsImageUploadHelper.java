package red.semipro.app.mypage.editseminar.contents.image.upload;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * 画像アップロード - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarContentsImageUploadHelper {

    private final SeminarImageHelper seminarImageHelper;
    private final SeminarSharedService seminarSharedService;

    /**
     * 画像をアップロードします
     *
     * @param form 画像アップロードフォーム
     * @return 画像URL
     * @throws IOException ファイルアップロード時の例外
     */
    public String upload(@Nonnull final EditSeminarContentsImageUploadForm form,
        @NotNull Account account)
        throws IOException {

        seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
            .id(form.getSeminarId())
            .accountId(account.getId())
            .build());

        final String path = seminarImageHelper.createContentsImagePath(form.getSeminarId(),
            FilenameUtils.getExtension(form.getImage().getOriginalFilename()));

        seminarImageHelper.upload(form.getImage(), path);
        return seminarImageHelper.getContentsImageUrl(path);
    }
}
