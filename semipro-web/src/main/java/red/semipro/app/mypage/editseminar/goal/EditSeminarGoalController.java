package red.semipro.app.mypage.editseminar.goal;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import red.semipro.domain.model.address.Prefecture;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.repository.seminar.SeminarCriteria;
import red.semipro.domain.service.address.CityService;
import red.semipro.domain.service.address.PrefectureService;
import red.semipro.domain.service.seminar.SeminarGoalService;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー目標 - controller
 */
@Controller
@RequestMapping("seminars/edit")
@TransactionTokenCheck(value = "editSeminarGoal")
@RequiredArgsConstructor
public class EditSeminarGoalController {

    private final SeminarSharedService seminarSharedService;
    private final EditSeminarGoalFormConverter formConverter;
    private final SeminarGoalService seminarGoalService;
    private final EditSeminarGoalFormValidator editSeminarGoalFormValidator;
    private final PrefectureService prefectureService;
    private final CityService cityService;

    @InitBinder("goalForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editSeminarGoalFormValidator);
    }

    @ModelAttribute("prefectures")
    public List<Prefecture> setupPrefectureList() {
        return prefectureService.findAll();
    }

    /**
     * セミナー目標フォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/goal")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) {

        final Seminar seminar = seminarSharedService.findOneWithDetails(SeminarCriteria.builder()
            .id(seminarId)
            .accountId(accountUserDetails.getAccount().getId())
            .build());

        if (!OpeningStatus.DRAFT.equals(seminar.getOpeningStatus())) {
            model.setViewName("redirect:/seminars/" + seminarId + "/preview");
            return model;
        }

        final EditSeminarGoalForm form = formConverter.convert(seminar.getGoal());

        if (Objects.nonNull(form.getPlaceForm().getPrefectureId())) {
            model.addObject("cities",
                cityService.findByPrefectureId(form.getPlaceForm().getPrefectureId()));
        }
        model.addObject("goalForm", form);
        model.setViewName("mypage/editseminar/goalForm");
        return model;
    }

    /**
     * セミナー目標を更新します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               セミナー目標フォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/goal/save")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("goalForm") @Validated EditSeminarGoalForm form,
        BindingResult result,
        RedirectAttributes redirectAttributes,
        ModelAndView model) {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/goal");
            return model;
        }

        if (result.hasErrors()) {
            if (Objects.nonNull(form.getPlaceForm().getPrefectureId())) {
                model.addObject("cities",
                    cityService.findByPrefectureId(form.getPlaceForm().getPrefectureId()));
            }
            model.setViewName("mypage/editseminar/goalForm");
            return model;
        }

        seminarGoalService.save(formConverter.convert(form), accountUserDetails.getAccount());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/" + form.getSeminarId() + "/preview");
        } else {
            redirectAttributes.addFlashAttribute("saved", true);
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/goal");
        }
        return model;
    }
}
