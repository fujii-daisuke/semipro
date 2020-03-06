package red.semipro.app.createseminar.contents.image.upload;

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
 * セミナーコンテンツ画像アップロード - controller
 */
@RestController
@RequestMapping("seminars/create/contents")
@RequiredArgsConstructor
public class CreateSeminarContentsImageUploadController {

    private final CreateSeminarContentsImageUploadHelper createSeminarContentsImageUploadHelper;

    /**
     * セミナーコンテンツの画像をアップロードします
     *
     * @param accountUserDetails AccountUserDetails
     * @param form               セミナーコンテンツ画像アップロードフォーム
     * @param result             BindingResult
     * @return ResponseEntity
     */
    @PostMapping("image/upload")
    public ResponseEntity upload(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @ModelAttribute @Validated CreateSeminarContentsImageUploadForm form, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("ファイルアップロードに失敗しました。画面を再度開き直してください。");
        }

        final String fileUrl;
        try {
            fileUrl = createSeminarContentsImageUploadHelper
                .upload(form, accountUserDetails.getAccount());
        } catch (IOException | IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("ファイルアップロードに失敗しました。時間を置いて再度アップロードしてください。");
        }

        return ResponseEntity.ok().body(fileUrl);
    }
}
