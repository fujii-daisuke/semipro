package red.semipro.app.mypage.editseminar.overview.image.delete;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.semipro.share.userdetails.AccountUserDetails;

/**
 * メイン画像削除 - controller
 */
@RestController
@RequestMapping("seminars/edit")
@RequiredArgsConstructor
public class EditSeminarOverviewImageDeleteController {

    private final EditSeminarOverviewImageDeleteHelper editSeminarOverviewImageDeleteHelper;

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

        editSeminarOverviewImageDeleteHelper
            .delete(seminarId, accountUserDetails.getAccount());

        return ResponseEntity.ok().body(Collections.EMPTY_MAP);
    }
}
