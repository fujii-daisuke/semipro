package red.semipro.app.mypage.createbankaccount;

import com.stripe.exception.StripeException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.service.bankaccount.BankAccountService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

@Controller
@RequestMapping("bankaccount")
@RequiredArgsConstructor
public class CreateBankAccountController {

    private final BankAccountService bankAccountService;
    private final CreateBankAccountFormConverter createBankAccountFormConverter;
    private final UpdateBankAccountFormConverter updateBankAccountFormConverter;

    @Value("${custom.stripe.public.key}")
    private String STRIPE_PUBLIC_KEY;

    @GetMapping
    public ModelAndView initialize(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        ModelAndView modelAndView)
        throws StripeException {

        modelAndView.addObject("createBankAccountForm", createBankAccountFormConverter
            .convert(bankAccountService.setup(accountUserDetails.getAccount().getId())));
        modelAndView.addObject("stripePublicKey", STRIPE_PUBLIC_KEY);
        modelAndView.setViewName("mypage/createbankaccount/bankAccountForm");
        return modelAndView;
    }

    @PostMapping("create")
    @ResponseBody
    public ResponseEntity create(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @ModelAttribute @Validated CreateBankAccountTokenForm form,
        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("トークンを設定して下さい。");
        }

        try {
            bankAccountService.create(accountUserDetails.getAccount().getId(), form.getToken());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("登録に失敗しました。時間を置いて再度登録して下さい。");
        }

        return ResponseEntity.ok().body(Collections.EMPTY_MAP);
    }

    @PostMapping("update")
    @ResponseBody
    public ResponseEntity update(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @ModelAttribute @Validated UpdateBankAccountForm form,
        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("入力に誤りがあります。");
        }

        try {
            bankAccountService.update(accountUserDetails.getAccount().getId(), updateBankAccountFormConverter.convert(form));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("更新に失敗しました。時間を置いて再度操作して下さい。");
        }

        return ResponseEntity.ok().body(Collections.EMPTY_MAP);
    }

    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails) {

        try {
            bankAccountService.delete(accountUserDetails.getAccount().getId());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("削除に失敗しました。時間を置いて再度操作して下さい。");
        }

        return ResponseEntity.ok().body(Collections.EMPTY_MAP);
    }
}
