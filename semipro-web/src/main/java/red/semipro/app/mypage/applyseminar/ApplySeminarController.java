package red.semipro.app.mypage.applyseminar;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー申請 - controller
 */
@RestController
@RequestMapping("seminars")
@RequiredArgsConstructor
public class ApplySeminarController {

    private final ApplySeminarHelper applySeminarHelper;

    /**
     * セミナー申請を受け付けます
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     */
    @GetMapping(value = "{seminarId}/apply")
    public ResponseEntity apply(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") Long seminarId) {

        final BindingResult bindingResult;
        try {
            bindingResult = applySeminarHelper.apply(seminarId, accountUserDetails.getAccount().getId());

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("該当のセミナーは申請済みの為、編集できません。");
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().body("必須項目が入力されていない箇所があります、再度ご確認下さい。");
        }
        return ResponseEntity.ok(Collections.EMPTY_MAP);
    }
}
