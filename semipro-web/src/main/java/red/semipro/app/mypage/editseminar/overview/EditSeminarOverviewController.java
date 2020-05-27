package red.semipro.app.mypage.editseminar.overview;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.seminar.SeminarOverviewService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー概要 - controller
 */
@Controller
@RequestMapping("seminars/edit")
@TransactionTokenCheck(value = "editSeminarOverview")
@RequiredArgsConstructor
public class EditSeminarOverviewController {

    private final SeminarSharedService seminarSharedService;
    private final EditSeminarOverviewFormConverter formConverter;
    private final SeminarOverviewService seminarOverviewService;

    /**
     * セミナー概要フォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/overview")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) {

        final Seminar seminar =
            seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
                .id(seminarId)
                .accountId(accountUserDetails.getAccount().getId())
                .build());

        if (!OpeningStatus.DRAFT.equals(seminar.getOpeningStatus())) {
            model.setViewName("redirect:/seminars/" + seminarId + "/preview");
            return model;
        }

        EditSeminarOverviewForm form = formConverter.convert(seminar.getOverview());

        model.addObject("overviewForm", form);
        model.setViewName("mypage/editseminar/overviewForm");
        return model;
    }

    /**
     * セミナー概要を更新します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               セミナー概要フォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/overview/save")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("overviewForm") @Validated EditSeminarOverviewForm form,
        BindingResult result,
        RedirectAttributes redirectAttributes,
        ModelAndView model) {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/overview");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("mypage/editseminar/overviewForm");
            return model;
        }

        seminarOverviewService.save(formConverter.convert(form), accountUserDetails.getAccount());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/" + form.getSeminarId() + "/preview");
        } else {
            redirectAttributes.addFlashAttribute("saved", true);
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/overview");
        }
        return model;
    }

}
