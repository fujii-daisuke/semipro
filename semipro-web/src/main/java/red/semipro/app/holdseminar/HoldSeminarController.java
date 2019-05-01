package red.semipro.app.holdseminar;

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
@RequestMapping("holds")
@TransactionTokenCheck(value = "holds")
public class HoldSeminarController {
    
    @Autowired
    private SeminarService seminarService;
    @Autowired
    private HoldSeminarFormValidator holdSeminarFormValidator;

    @InitBinder("holdSeminarForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(holdSeminarFormValidator);
    }
    
    @ModelAttribute("holdSeminarForm")
    public HoldSeminarForm setupHoldSeminarForm() {
        return new HoldSeminarForm();
    }
    
    @GetMapping
    public ModelAndView clearSessionAtStart(SessionStatus sesseionStatus, ModelAndView model) {
        sesseionStatus.setComplete();
        model.setViewName("forward:/holds/input");
        return model;
    }
    
    @GetMapping(value = "input")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView input(ModelAndView model) {
        model.setViewName("holdseminar/input");
        return model;
    }
    
    @PostMapping(value = "register")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView register(@Validated HoldSeminarForm form, 
            BindingResult result,
            ModelAndView model) throws Exception {
        
        if (result.hasErrors()) {
            model.setViewName("holdseminar/input");
            return model;
        }
        
        Seminar seminar = Seminar.builder()
            .openingStatus(OpeningStatus.DRAFT)
            .title(form.getTitle())
            .seminarType(SeminarType.valueOf(form.getSeminarType()))
            .build();
        
        seminar = seminarService.register(seminar);
        model.setViewName("redirect:/holds/" + seminar.getId() + "/detail");
        return model;
    }
    
    @GetMapping(value = "completed")
    public ModelAndView completed(ModelAndView model) {
        model.setViewName("registrationseminar/completed");
        return model;
    }
}
