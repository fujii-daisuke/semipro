package red.semipro.app.createseminar.overview.image.upload;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * メイン画像アップロード - controller
 */
@RestController
@RequestMapping("seminars/create/overview")
@RequiredArgsConstructor
public class CreateSeminarOverviewImageUploadController {

    private final CreateSeminarOverviewImageUploadHelper createSeminarOverviewImageUploadHelper;

    /**
     * メイン画像をアップロードします
     *
     * @param accountUserDetails AccountUserDetails
     * @param form               メイン画像アップロードフォーム
     * @param result             BindingResult
     * @return ResponseEntity
     */
    @PostMapping("image/upload")
    public ResponseEntity upload(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @ModelAttribute @Validated CreateSeminarOverviewImageUploadForm form, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("ファイルアップロードに失敗しました。画面を再度開き直してください。");
        }

        final String fileUrl;
        try {
            fileUrl = createSeminarOverviewImageUploadHelper
                .upload(form, accountUserDetails.getAccount());
        } catch (IOException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("ファイルアップロードに失敗しました。時間を置いて再度アップロードしてください。");
        }

        return ResponseEntity.ok().body(fileUrl);
    }
}
