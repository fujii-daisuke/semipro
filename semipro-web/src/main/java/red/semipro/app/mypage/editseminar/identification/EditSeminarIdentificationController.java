package red.semipro.app.mypage.editseminar.identification;

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
import red.semipro.domain.service.userdetails.AccountUserDetails;

@Controller
@RequestMapping("seminars/edit")
@TransactionTokenCheck(value = "editSeminarIdentification")
@RequiredArgsConstructor
public class EditSeminarIdentificationController {

    private final EditSeminarIdentificationHelper editSeminarIdentificationHelper;
    private final EditSeminarIdentificationValidator editSeminarIdentificationValidator;

    @InitBinder("identificationForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editSeminarIdentificationValidator);
    }

    /**
     * 本人確認ォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/identification")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) {

        EditSeminarIdentificationForm form =
            editSeminarIdentificationHelper
                .setupForm(seminarId, accountUserDetails.getAccount().getId());

        model.addObject("identificationForm", form);
        model.setViewName("mypage/editseminar/identificationForm");
        return model;
    }

    /**
     * 本人確認を更新します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               本人確認フォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/identification/save")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("identificationForm") @Validated EditSeminarIdentificationForm form,
        BindingResult result,
        ModelAndView model) {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/identification");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("mypage/editseminar/identificationForm");
            return model;
        }
        editSeminarIdentificationHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/identification");
        }
        return model;
    }
}
