package red.semipro.app.createseminar.support;

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
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナーサポート - controller
 */
@Controller
@RequestMapping("seminars/create")
@TransactionTokenCheck(value = "createSeminarSupport")
@RequiredArgsConstructor
public class CreateSeminarSupportController {

    private final CreateSeminarSupportHelper createSeminarSupportHelper;

    /**
     * セミナーサポートフォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/support")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) throws IllegalAccessException {

        CreateSeminarSupportForm form =
            createSeminarSupportHelper
                .setupForm(seminarId, accountUserDetails.getAccount().getId());

        model.addObject("supportForm", form);
        model.setViewName("createseminar/supportForm");
        return model;
    }

    /**
     * セミナーサポートを更新します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               セミナーサポートフォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/support/save")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("supportForm") @Validated CreateSeminarSupportForm form,
        BindingResult result,
        ModelAndView model) throws IllegalAccessException {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/support");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("createseminar/supportForm");
            return model;
        }
        createSeminarSupportHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/support");
        }
        return model;
    }
}
