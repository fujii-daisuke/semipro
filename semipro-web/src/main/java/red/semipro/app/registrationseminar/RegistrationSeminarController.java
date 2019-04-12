package red.semipro.app.registrationseminar;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarService;

@Controller
@RequestMapping("seminars/registration")
@TransactionTokenCheck(value = "seminars")
public class RegistrationSeminarController {
    
    @Autowired
    SeminarService seminarService;
    @Autowired
    RegistrationSeminarFormValidator registrationSeminarFormValidator;

    @InitBinder("registrationSeminarForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registrationSeminarFormValidator);
    }
    
    @ModelAttribute("registrationSeminarForm")
    public RegistrationSeminarForm setupRegistrationSeminarForm() {
        return new RegistrationSeminarForm();
    }
    
    @GetMapping
    public ModelAndView clearSessionAtStart(SessionStatus sesseionStatus, ModelAndView model) {
        sesseionStatus.setComplete();
        model.setViewName("forward:/seminars/registration/input");
        return model;
    }
    
    @GetMapping(value = "input")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(ModelAndView model) {
        model.setViewName("registrationseminar/input");
        return model;
    }
    
    @PostMapping(value = "register")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView register(@Validated RegistrationSeminarForm form, 
            BindingResult result,
            ModelAndView model) throws Exception {
        
        if (result.hasErrors()) {
            model.setViewName("registrationseminar/input");
            return model;
        }
        
        Seminar seminar = Seminar.builder()
            .openingStatus(OpeningStatus.DRAFT)
            .seminarType(SeminarType.valueOf(form.getSeminarType()))
            .title(form.getTitle())
            .startedAt(LocalDateTime.of(form.getStartedAtYear(), form.getStartedAtMonth(), form.getStartedAtDay(), form.getStartedAtHour(), form.getStartedAtMinute()))
            .endedAt(LocalDateTime.of(form.getEndedAtYear(), form.getEndedAtMonth(), form.getEndedAtDay(), form.getEndedAtHour(), form.getEndedAtMinute()))
            .build();
        
        seminar = seminarService.register(seminar);
        model.setViewName("redirect:/seminar/" + seminar.getId() + "/update");
        return model;
    }
    
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("registrationseminar/completed");
        return model;
    }
}
