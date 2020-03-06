package red.semipro.app.createseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.userdetails.AccountUserDetails;

@Controller
@RequestMapping("seminars/create")
@RequiredArgsConstructor
public class SeminarCreateController {

    private final CreateSeminarHelper createSeminarHelper;

    /**
     * セミナー初期状態を作成します
     *
     * @param accountUserDetails AccountUserDetails
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping
    public ModelAndView initialize(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails, ModelAndView model) {
        Seminar seminar = createSeminarHelper.initialize(accountUserDetails.getAccount());
        model.setViewName("redirect:/seminars/create/" + seminar.getId() + "/goal");
        return model;
    }

}
