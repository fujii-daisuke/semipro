package red.semipro.app.createseminar.goal;

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
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import red.semipro.domain.model.Prefecture;
import red.semipro.domain.service.city.CityService;
import red.semipro.domain.service.prefecture.PrefectureService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー目標 - controller
 */
@Controller
@RequestMapping("seminars/create")
@TransactionTokenCheck(value = "createSeminarGoal")
@RequiredArgsConstructor
public class CreateSeminarGoalController {

    private final CreateSeminarGoalFormValidator createSeminarGoalFormValidator;
    private final PrefectureService prefectureService;
    private final CityService cityService;
    private final CreateSeminarGoalHelper createSeminarGoalHelper;

    @InitBinder("goalForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createSeminarGoalFormValidator);
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
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) throws IllegalAccessException {

        CreateSeminarGoalForm form =
            createSeminarGoalHelper.setupForm(seminarId, accountUserDetails.getAccount().getId());

        if (Objects.nonNull(form.getPlaceForm().getPrefectureId())) {
            model.addObject("cities",
                cityService.findByPrefectureId(form.getPlaceForm().getPrefectureId()));
        }
        model.addObject("goalForm", form);
        model.setViewName("createseminar/goalForm");
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
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("goalForm") @Validated CreateSeminarGoalForm form,
        BindingResult result,
        ModelAndView model) throws IllegalAccessException {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/goal");
            return model;
        }

        if (result.hasErrors()) {
            if (Objects.nonNull(form.getPlaceForm().getPrefectureId())) {
                model.addObject("cities",
                    cityService.findByPrefectureId(form.getPlaceForm().getPrefectureId()));
            }
            model.setViewName("createseminar/goalForm");
            return model;
        }
        createSeminarGoalHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/goal");
        }
        return model;
    }
}
