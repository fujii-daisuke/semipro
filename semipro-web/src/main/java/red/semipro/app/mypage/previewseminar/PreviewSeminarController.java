package red.semipro.app.mypage.previewseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.service.userdetails.AccountUserDetails;
import red.semipro.domain.aws.service.SeminarImageService;

/**
 * セミナープレビュー - controller
 */
@Controller
@RequestMapping("seminars")
@RequiredArgsConstructor
public class PreviewSeminarController {

    private final SeminarSharedService seminarSharedService;
    private final SeminarImageService seminarImageService;

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

        Seminar seminar =
            seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
                .id(seminarId)
                .accountId(accountUserDetails.getAccount().getId())
                .build());

        model.addObject("seminar", seminar);

        model.setViewName("mypage/previewseminar/preview");
        return model;
    }

}
