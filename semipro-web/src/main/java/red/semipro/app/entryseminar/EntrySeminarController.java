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
import red.semipro.domain.helper.stripe.customercard.CustomerCard;
import red.semipro.domain.helper.stripe.customercard.StripeCardHelper;
import red.semipro.domain.service.seminar.SeminarSharedService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

/**
 * セミナー予約 - controller
 */
@Controller
@RequestMapping(value = "seminars")
@RequiredArgsConstructor
public class EntrySeminarController {

    private final EntrySeminarHelper entrySeminarHelper;
    private final SeminarSharedService seminarSharedService;
    private final StripeCardHelper stripeCardHelper;


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

        model.addObject("seminar",
            seminarSharedService
                .findOneWithDetailsByIdAndOpeningStatusAndSeminarTicketId(
                    seminarId, OpeningStatus.OPENING, ticketId));

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

        model.addObject("seminar",
            seminarSharedService
                .findOneWithDetailsByIdAndOpeningStatusAndSeminarTicketId(
                    seminarId, OpeningStatus.OPENING, ticketId));

        if (result.hasErrors()) {
            model.addObject("customerCards", customerCards);
            model.setViewName("entryseminar/entryForm");
            return model;
        }

        model.addObject("selectedCard",
            customerCards.stream()
                .filter(c -> c.getId().equals(form.getSelectedStripeCustomerCardId()))
                .findFirst()
                .orElseThrow());
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

        model.addObject("seminar",
            seminarSharedService
                .findOneWithDetailsByIdAndOpeningStatusAndSeminarTicketId(
                    seminarId, OpeningStatus.OPENING, ticketId));

        if (result.hasErrors()) {
            model.addObject("customerCards", entrySeminarHelper
                .findStripeCustomerCardList(accountUserDetails.getAccount().getId()));
            model.setViewName("entryseminar/entryForm");
            return model;
        }

        entrySeminarHelper.entry(seminarId,
            ticketId,
            accountUserDetails.getAccount().getId(),
            form.getSelectedStripeCustomerCardId());

        model.setViewName("redirect:/seminars/"+ seminarId + "/detail");
        return model;
    }
}
