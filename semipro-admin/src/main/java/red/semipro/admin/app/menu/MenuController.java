package red.semipro.admin.app.menu;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.service.userdetails.AdminUserDetails;

/**
 * メニュー - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/", "menu"})
public class MenuController {

    /**
     * メニュー画面を表示します
     *
     * @param adminUserDetails AdminUserDetails
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView menu(@AuthenticationPrincipal final AdminUserDetails adminUserDetails,
        ModelAndView model) {

        if (Objects.isNull(adminUserDetails)) {
            model.setViewName("redirect:/login");
            return model;
        }

        model.setViewName("menu/menu");
        return model;
    }
}
