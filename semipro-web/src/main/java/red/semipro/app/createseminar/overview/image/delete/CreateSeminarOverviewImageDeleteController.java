package red.semipro.app.createseminar.overview.image.delete;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * メイン画像削除 - controller
 */
@RestController
@RequestMapping("seminars/create")
@RequiredArgsConstructor
public class CreateSeminarOverviewImageDeleteController {

    private final CreateSeminarOverviewImageDeleteHelper createSeminarOverviewImageDeleteHelper;

    /**
     * メイン画像を削除します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @return ResponseEntity
     */
    @GetMapping("{seminarId}/overview/image/delete")
    public ResponseEntity delete(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId) {

        try {
            createSeminarOverviewImageDeleteHelper
                .delete(seminarId, accountUserDetails.getAccount());
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("ファイル削除に失敗しました。画面を再表示後、再度実施してください。");
        }

        return ResponseEntity.ok().body(Collections.EMPTY_MAP);
    }
}
