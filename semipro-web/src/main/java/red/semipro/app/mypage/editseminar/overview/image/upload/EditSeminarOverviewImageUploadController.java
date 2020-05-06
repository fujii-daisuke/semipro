package red.semipro.app.mypage.editseminar.overview.image.upload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("seminars/edit/overview")
@RequiredArgsConstructor
@Slf4j
public class EditSeminarOverviewImageUploadController {

    private final EditSeminarOverviewImageUploadHelper editSeminarOverviewImageUploadHelper;

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
        @ModelAttribute @Validated EditSeminarOverviewImageUploadForm form, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("ファイルサイズが上限をオーバーしています。");
        }

        final String fileUrl;
        try {
            fileUrl =
                editSeminarOverviewImageUploadHelper.upload(form, accountUserDetails.getAccount());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("ファイルアップロードに失敗しました。時間を置いて再度アップロードしてください。");
        }

        return ResponseEntity.ok().body(fileUrl);
    }
}
