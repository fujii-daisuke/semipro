package red.semipro.app.activation;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * アクティベーション - controller
 */
@Controller
@RequestMapping(value = "activation")
@RequiredArgsConstructor
@Slf4j
public class ActivationController {

    private final ActivationHelper activationHelper;

    /**
     * アクティベーションのリクエストを受け付けます
     *
     * @param activationKey アクティベーションキー
     * @param model         ModelAndView
     * @param locale        Locale
     * @return ModelAndView
     * @throws Exception アクティベーションキーの復号化失敗時に発生します
     */
    @GetMapping(value = "{activationKey}/register")
    public ModelAndView activation(@PathVariable("activationKey") String activationKey,
        ModelAndView model, Locale locale) throws Exception {

        try {
            activationHelper.activate(activationKey, locale);
        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
            model.setViewName("redirect:/activation/failure");
            return model;
        }
        model.setViewName("redirect:/activation/completed");
        return model;
    }

    /**
     * アクティベーション失敗画面を表示します
     *
     * @param model ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "failure")
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
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("activation/completed");
        return model;
    }
}
