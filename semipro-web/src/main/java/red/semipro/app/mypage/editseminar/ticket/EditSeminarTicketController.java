package red.semipro.app.mypage.editseminar.ticket;

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
import red.semipro.share.userdetails.AccountUserDetails;

/**
 * セミナーチケット - controller
 */
@Controller
@RequestMapping("seminars/edit")
@TransactionTokenCheck(value = "editSeminarTicket")
@RequiredArgsConstructor
public class EditSeminarTicketController {

    private final EditSeminarTicketHelper editSeminarTicketHelper;

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
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") Long seminarId,
        ModelAndView model) throws IllegalAccessException {

        EditSeminarTicketForm form =
            editSeminarTicketHelper
                .setupForm(seminarId, accountUserDetails.getAccount().getId());

        model.addObject("ticketForm", form);
        model.setViewName("mypage/editseminar/ticketForm");
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
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("ticketForm") @Validated EditSeminarTicketForm form,
        BindingResult result,
        ModelAndView model) throws IllegalAccessException {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/ticket");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("mypage/editseminar/ticketForm");
            return model;
        }
        editSeminarTicketHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/edit/" + seminarId + "/ticket");
        }
        return model;
    }

}
