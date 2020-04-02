package red.semipro.app.mypage.previewseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナープレビュー - controller
 */
@Controller
@RequestMapping("seminars")
@RequiredArgsConstructor
public class PreviewSeminarController {

    private final PreviewSeminarHelper previewSeminarHelper;

    /**
     * セミナープレビュー画面を表示します
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/preview")
    public ModelAndView detail(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") final Long seminarId,
        ModelAndView model) {

        model.addObject("seminar", previewSeminarHelper
            .findSeminarDetail(seminarId, accountUserDetails.getAccount().getId()));

        model.setViewName("mypage/previewseminar/preview");
        return model;
    }

}
