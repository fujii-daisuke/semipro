package red.semipro.app.entryseminar;

import com.stripe.exception.StripeException;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.service.entry.EntryInput;
import red.semipro.domain.service.entry.EntrySeminar;
import red.semipro.domain.service.entry.EntryService;
import red.semipro.domain.service.userdetails.AccountUserDetails;
import red.semipro.domain.stripe.repository.customercard.CustomerCard;

/**
 * セミナー予約 - controller
 */
@Controller
@RequestMapping(value = "seminars")
@RequiredArgsConstructor
public class EntrySeminarController {

    private final EntrySeminarHelper entrySeminarHelper;
    private final EntryService entryService;

    /**
     * セミナー申し込みクレジットカード情報入力画面を表示します
     *
     * @param accountUserDetails AccountUserDetails
     * @param seminarId          セミナーID
     * @param ticketId           チケットID
     * @param model              ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/entry/{ticketId}")
    public ModelAndView entry(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") final Long seminarId,
        @PathVariable final Long ticketId,
        ModelAndView model) throws StripeException {

        List<CustomerCard> customerCards = entrySeminarHelper
            .findStripeCustomerCardList(accountUserDetails.getAccount().getId());

        model.addObject("entrySeminar",
            entryService
                .findEntrySeminar(
                    seminarId, OpeningStatus.OPENING, ticketId,
                    accountUserDetails.getAccount().getId()));

        model.addObject("customerCards", customerCards);
        model.addObject("entrySeminarForm", EntrySeminarForm.builder()
            .selectedStripeCustomerCardId(
                customerCards.isEmpty() ? null : customerCards.get(0).getId())
            .build());
        model.setViewName("entryseminar/entryForm");
        return model;
    }

    @PostMapping(value = "{seminarId}/entry/{ticketId}/confirm")
    public ModelAndView confirm(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") final Long seminarId,
        @PathVariable final Long ticketId,
        @ModelAttribute("entrySeminarForm") @Validated EntrySeminarForm form,
        BindingResult result,
        ModelAndView model) throws StripeException {

        List<CustomerCard> customerCards = entrySeminarHelper
            .findStripeCustomerCardList(accountUserDetails.getAccount().getId());

        final EntrySeminar entrySeminar = entryService.findEntrySeminar(seminarId,
            OpeningStatus.OPENING,
            ticketId,
            accountUserDetails.getAccount().getId());

        model.addObject("entrySeminar", entrySeminar);

        result = entrySeminarHelper.validate(form, entrySeminar.getSelectedTicket(), result);

        if (result.hasErrors()) {
            model.addObject("customerCards", customerCards);
            model.setViewName("entryseminar/entryForm");
            return model;
        }

        model.addObject("selectedCard",
            customerCards.stream()
                .filter(c -> c.getId().equals(form.getSelectedStripeCustomerCardId()))
                .findFirst()
                .orElse(null));
        model.setViewName("entryseminar/confirm");
        return model;
    }

    @PostMapping(value = "{seminarId}/entry/{ticketId}/complete")
    public ModelAndView complete(
        @AuthenticationPrincipal final AccountUserDetails accountUserDetails,
        @PathVariable("seminarId") final Long seminarId,
        @PathVariable final Long ticketId,
        @ModelAttribute("entrySeminarForm") @Validated EntrySeminarForm form,
        BindingResult result,
        ModelAndView model) throws StripeException {

        entryService.entry(EntryInput.builder()
            .seminarId(seminarId)
            .ticketId(ticketId)
            .entryAccountId(accountUserDetails.getAccount().getId())
            .stripeCustomerCardId(form.getSelectedStripeCustomerCardId())
            .build());

        model.setViewName("redirect:/seminars/" + seminarId + "/detail");
        return model;
    }
}
