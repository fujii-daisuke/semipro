package red.semipro.app.mypage.editseminar.contents;

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
 * セミナーコンテンツ - controller
 */
@Controller
@RequestMapping("seminars/edit")
@TransactionTokenCheck(value = "editSeminarContents")
@RequiredArgsConstructor
public class EditSeminarContentsController {

    private final EditSeminarContentsHelper editSeminarContentsHelper;

    /**
     * セミナーコンテンツフォームを表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/contents")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") final Long seminarId,
        ModelAndView model) {

        EditSeminarContentsForm form =
            editSeminarContentsHelper
                .setupForm(seminarId, accountUserDetails.getAccount().getId());

        model.addObject("contentsForm", form);
        model.setViewName("mypage/editseminar/contentsForm");
        return model;
    }

    /**
     * セミナーコンテンツを更新します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param form               セミナーコンテンツフォーム
     * @param result             BindingResult
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @PostMapping(value = "{seminarId}/contents/save")
    @TransactionTokenCheck(value = "edit", type = TransactionTokenType.IN)
    public ModelAndView save(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
        @PathVariable(name = "seminarId") Long seminarId,
        @RequestParam("action") String action,
        @ModelAttribute("contentsForm") @Validated EditSeminarContentsForm form,
        BindingResult result,
        ModelAndView model) {

        if (!Objects.equals(seminarId, form.getSeminarId())) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/contents");
            return model;
        }

        if (result.hasErrors()) {
            model.setViewName("mypage/editseminar/contentsForm");
            return model;
        }
        editSeminarContentsHelper.save(form, accountUserDetails.getAccount().getId());

        if (Objects.equals(action, "preview")) {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/preview");
        } else {
            model.setViewName("redirect:/seminars/edit/" + form.getSeminarId() + "/contents");
        }
        return model;
    }
}
