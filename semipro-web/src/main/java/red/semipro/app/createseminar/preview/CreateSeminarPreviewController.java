package red.semipro.app.createseminar.preview;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナープレビュー - controller
 */
@Controller
@RequestMapping("seminars/create")
@TransactionTokenCheck(value = "createSeminarPreview")
@RequiredArgsConstructor
public class CreateSeminarPreviewController {

    private final CreateSeminarPreviewHelper createSeminarPreviewHelper;

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
        ModelAndView model) throws IllegalAccessException {

        model.addObject("seminarDetail", createSeminarPreviewHelper
            .findSeminarDetail(seminarId, accountUserDetails.getAccount().getId()));
        model.setViewName("createseminar/preview");
        return model;
    }

}
