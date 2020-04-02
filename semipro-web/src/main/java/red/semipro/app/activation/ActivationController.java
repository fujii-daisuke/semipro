package red.semipro.app.activation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.service.account.AccountService;

/**
 * アクティベーション - controller
 */
@Controller
@RequestMapping(value = "accounts")
@RequiredArgsConstructor
@Slf4j
public class ActivationController {

    private final AccountService accountService;

    /**
     * アクティベーションのリクエストを受け付けます
     *
     * @param activationKey アクティベーションキー
     * @param model         ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{activationKey}/activation")
    public ModelAndView activation(@PathVariable("activationKey") String activationKey,
        ModelAndView model) {

        accountService.activate(activationKey);

        model.setViewName("redirect:/accounts/activation/completed");
        return model;
    }

    /**
     * アクティベーション失敗画面を表示します
     *
     * @param model ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "activation/failure")
    public ModelAndView failure(ModelAndView model) {
        model.setViewName("activation/failure");
        return model;
    }

    /**
     * アクティベーション成功画面を表示します
     *
     * @param model ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "activation/completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("activation/completed");
        return model;
    }
}
