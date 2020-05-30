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
import red.semipro.domain.aws.service.SeminarImageService;

/**
 * 画像アップロード - helper
 */
@Component
@RequiredArgsConstructor
public class EditSeminarContentsImageUploadHelper {

    private final SeminarImageService seminarImageService;
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

        final String path = seminarImageService.createContentsImagePath(form.getSeminarId(),
            FilenameUtils.getExtension(form.getImage().getOriginalFilename()));

        seminarImageService.upload(form.getImage(), path);
        return seminarImageService.getContentsImageUrl(path);
    }
}
