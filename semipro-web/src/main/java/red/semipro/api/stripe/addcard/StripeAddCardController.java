package red.semipro.api.stripe.addcard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * Stripe カード情報登録 - controller
 */
@RestController
@RequestMapping("stripe")
@RequiredArgsConstructor
public class StripeAddCardController {

    private final StripeAddCardHelper stripeAddCardHelper;

    /**
     * カード情報を登録します
     *
     * @param accountUserDetails AccountUserDetails
     * @param token              Stripeクレジットカード作成トークン
     */
    @PostMapping(value = "add-card")
    public ResponseEntity addCard(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @RequestParam("token") String token) {

        try {
            return ResponseEntity
                .ok(stripeAddCardHelper.addCard(accountUserDetails.getAccount().getId(), token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("エラーが発生しました、お手数ですが、時間を置いて再度アクセスして下さい");
        }
    }
}
