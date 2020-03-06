package red.semipro.app.signup;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

/**
 * 会員登録 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "signup")
@TransactionTokenCheck(value = "signup")
public class SignUpController {

    private final SignUpFormConverter signUpFormConverter;
    private final SignUpHelper signUpHelper;
    private final SignUpFormValidator signUpFormValidator;

    /**
     * 会員登録バリデーションを登録
     *
     * @param webDataBinder バインダー
     */
    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    /**
     * 会員登録フォームの初期化
     *
     * @return 会員登録フォーム
     */
    @ModelAttribute("signUpForm")
    public SignUpForm setupSignUpForm() {
        return new SignUpForm();
    }

    /**
     * 会員登録フォームを表示する
     *
     * @param model ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    @TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
    public ModelAndView input(ModelAndView model) {
        model.setViewName("signup/input");
        return model;
    }

    /**
     * 会員登録を行う
     *
     * @param signupForm 会員登録フォーム
     * @param result     BindingResult
     * @param locale     ロケール
     * @param model      ModelAndView
     * @return ModelAndView
     * @throws Exception パスワード暗号化失敗
     */
    @PostMapping(value = "register")
    @TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
    public ModelAndView register(@Validated SignUpForm signupForm,
        BindingResult result,
        Locale locale,
        ModelAndView model) throws Exception {

        if (result.hasErrors()) {
            model.setViewName("signup/input");
            return model;
        }

        signUpHelper.register(signUpFormConverter.convert(signupForm), locale);

        model.setViewName("redirect:/signup/completed");
        return model;
    }

    /**
     * 会員登録完了画面を表示する
     *
     * @param model ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("signup/completed");
        return model;
    }
}
