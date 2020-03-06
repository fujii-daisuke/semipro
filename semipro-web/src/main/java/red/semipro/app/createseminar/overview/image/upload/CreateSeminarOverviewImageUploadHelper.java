package red.semipro.app.createseminar.overview.image.upload;

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
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.share.seminar.SeminarImageHelper;

/**
 * メイン画像アップロード - helper
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CreateSeminarOverviewImageUploadHelper {

    private final SeminarService seminarService;
    private final SeminarImageHelper seminarImageHelper;
    private final SeminarOverviewService seminarOverviewService;

    /**
     * メイン画像をアップロードします
     *
     * @param form メイン画像アップロードフォーム
     * @return 画像URL
     * @throws IOException ファイルアップロード時の例外
     */
    public String upload(@Nonnull final CreateSeminarOverviewImageUploadForm form,
        @NotNull final Account account)
        throws IOException, IllegalAccessException {

        if (Objects.isNull(
            seminarService.findOneBy(form.getSeminarId(), account.getId(),
                OpeningStatus.DRAFT))) {
            throw new IllegalAccessException("seminar illegal access.");
        }

        final String extension = FilenameUtils
            .getExtension(form.getImage().getOriginalFilename());

        seminarImageHelper.upload(form.getImage(),
            seminarImageHelper.createMainImagePath(form.getSeminarId(), extension));
        seminarOverviewService.updateMainImageExtension(form.getSeminarId(), extension);

        return seminarImageHelper.getMainImageUrl(form.getSeminarId(), extension);
    }
}
