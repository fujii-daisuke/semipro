package red.semipro.app.mypage.createseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.share.userdetails.AccountUserDetails;

@Controller
@RequestMapping("seminars/create")
@RequiredArgsConstructor
public class CreateSeminarController {

    private final SeminarService seminarService;

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
        Seminar seminar = seminarService.initialize(accountUserDetails.getAccount().getId());
        model.setViewName("redirect:/seminars/edit/" + seminar.getId() + "/goal");
        return model;
    }

}
