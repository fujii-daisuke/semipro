package red.semipro.app.createseminar.ticket;

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
 * セミナーチケット - controller
 */
@Controller
@RequestMapping("seminars/create")
@TransactionTokenCheck(value = "createSeminarTicket")
@RequiredArgsConstructor
public class CreateSeminarTicketController {

    private final CreateSeminarTicketHelper createSeminarTicketHelper;

    /**
     * セミナーチケットフォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     * @throws IllegalAccessException
     */
    @GetMapping(value = "{seminarId}/ticket")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") Long seminarId,
        ModelAndView model) throws IllegalAccessException {

        CreateSeminarTicketForm form =
            createSeminarTicketHelper
                .setupForm(seminarId, accountUserDetails.getAccount().getId());

        model.addObject("ticketForm", form);
        model.setViewName("createseminar/ticketForm");
        return model;
    }

    /**
     * セミナーチケットを登録します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               セミナーチケットフォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/ticket/save")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("ticketForm") @Validated CreateSeminarTicketForm form,
        BindingResult result,
        ModelAndView model) throws IllegalAccessException {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/ticket");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("createseminar/ticketForm");
            return model;
        }
        createSeminarTicketHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/create/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/create/" + seminarId + "/ticket");
        }
        return model;
    }

}
