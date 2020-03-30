package red.semipro.admin.app.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.admin.share.userdetails.AdminUserDetails;

/**
 * ログイン - controller
 */
@Controller
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {

    /**
     * ログインフォームを初期化します
     *
     * @return ログインフォーム
     */
    @ModelAttribute("loginForm")
    public LoginForm LoginForm() {
        return new LoginForm();
    }

    /**
     * ログインフォームを表示します
     *
     * @param adminUserDetails  AdminUserDetails
     * @param modelAndView ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView input(@AuthenticationPrincipal AdminUserDetails adminUserDetails,
        ModelAndView modelAndView) {
        if (adminUserDetails != null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.setViewName("login/login");
        return modelAndView;
    }
}