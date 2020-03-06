package red.semipro.app.createseminar.contents.image.upload;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * 画像アップロード - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarContentsImageUploadHelper {

    private final SeminarService seminarService;
    private final SeminarImageHelper seminarImageHelper;

    /**
     * 画像をアップロードします
     *
     * @param form 画像アップロードフォーム
     * @return 画像URL
     * @throws IllegalAccessException 編集可能でないセミナーへのアクセス
     * @throws IOException            ファイルアップロード時の例外
     */
    public String upload(@Nonnull final CreateSeminarContentsImageUploadForm form,
        @NotNull Account account)
        throws IOException, IllegalAccessException {

        if (Objects.isNull(
            seminarService.findOneBy(form.getSeminarId(), account.getId(),
                OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        final String path = seminarImageHelper.createContentsImagePath(form.getSeminarId(),
            FilenameUtils.getExtension(form.getImage().getOriginalFilename()));

        seminarImageHelper.upload(form.getImage(), path);
        return seminarImageHelper.getContentsImageUrl(path);
    }
}
