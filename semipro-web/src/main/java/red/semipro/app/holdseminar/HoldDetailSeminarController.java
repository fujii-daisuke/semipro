package red.semipro.app.holdseminar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import red.semipro.business.account.AccountUserDetails;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarService;

@Controller
@RequestMapping("holds/detail")
@TransactionTokenCheck(value = "holds/detail")
public class HoldDetailSeminarController {

    @Autowired
    private SeminarService seminarService;
    @Autowired
    private HoldDetailSeminarFormValidator holdDetailSeminarFormValidator;

    @InitBinder("holdDetailSeminarForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(holdDetailSeminarFormValidator);
    }
    
    @ModelAttribute("holdDetailSeminarForm")
    public HoldDetailSeminarForm setupHoldDetailSeminarForm() {
        return new HoldDetailSeminarForm();
    }
    
    @GetMapping(value="{seminarId}/input")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(@PathVariable("seminarId") String seminarId,
                ModelAndView model) {
        model.addObject("seminarId", seminarId);
        model.setViewName("holdseminar/detail/input");
        return model;
    }
    
    @PostMapping(value = "{seminarId}/register")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView register(@AuthenticationPrincipal AccountUserDetails account,
            @PathVariable("seminarId") String seminarId,
            @Validated HoldDetailSeminarForm form, 
            BindingResult result,
            ModelAndView model) {
        
        if (result.hasErrors()) {
            model.addObject("seminarId", seminarId);
            model.setViewName("holdseminar/detail/input");
            return model;
        }
        
        Seminar seminar = seminarService.findOneWithDetails(Long.valueOf(seminarId));
        seminar.setSummary(form.getSummary());
        seminar.setContents(form.getContents());
        seminar.setEntryStartingAt(LocalDateTime.of(LocalDate.parse(form.getEntryStartingDate()), LocalTime.parse(form.getEntryStartingTime())));
        seminar.setEntryEndingAt(LocalDateTime.of(LocalDate.parse(form.getEntryEndingDate()), LocalTime.parse(form.getEntryEndingTime())));
        seminar.setUpdatedAt(LocalDateTime.now());
        seminarService.update(seminar);
        
        model.setViewName("redirect:/holds/preview/" + seminarId);
        return model;
    }
}
