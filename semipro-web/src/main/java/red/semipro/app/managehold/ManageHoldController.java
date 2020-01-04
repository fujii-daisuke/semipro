package red.semipro.app.managehold;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import red.semipro.business.aws.S3StorageService;
import red.semipro.domain.enums.ProviderId;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.model.Prefecture;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.city.CityService;
import red.semipro.domain.service.prefecture.PrefectureService;
import red.semipro.domain.service.seminar.SeminarService;
import red.semipro.domain.service.userdetails.AccountUserDetails;

@Controller
@RequestMapping("holds")
@TransactionTokenCheck(value = "holds")
public class ManageHoldController {
    
    @Autowired
    private SeminarService seminarService;
    @Autowired
    private ManageHoldBasicFormValidator manageHoldBasicFormValidator;
    @Autowired
    private PrefectureService prefectureService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ManageHoldHelper manageHoldHelper;
    @Autowired
    private S3StorageService s3StorageService;
    
    @InitBinder("manageHoldBasicForm")
    public void initManageHoldBasicBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(manageHoldBasicFormValidator);
    }
    
    @ModelAttribute("manageHoldBasicForm")
    public ManageHoldBasicForm setupManageHoldBasicForm() {
        return new ManageHoldBasicForm();
    }
    
    @Autowired
    private ManageHoldAdvancedFormValidator manageHoldAdvancedFormValidator;

    @InitBinder("manageHoldAdvancedForm")
    public void initManageHoldAdvancedBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(manageHoldAdvancedFormValidator);
    }
    
    @ModelAttribute("manageHoldAdvancedForm")
    public ManageHoldAdvancedForm setupManageHoldAdvancedForm() {
        return new ManageHoldAdvancedForm();
    }
    
    @Autowired
    private ManageHoldTicketFormValidator manageHoldTicketFormValidator;

    @InitBinder("manageHoldTicketForm")
    public void initManageHoldTicketBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(manageHoldTicketFormValidator);
    }
    
    @ModelAttribute("manageHoldTicketForm")
    public ManageHoldTicketForm setupManageHoldTicketForm() {
        return new ManageHoldTicketForm();
    }
    
    @ModelAttribute("prefectures")
    public List<Prefecture> setupPrefectureList() {
        return prefectureService.findAll();
    }
    
    @GetMapping
    public ModelAndView clearSessionAtStart(SessionStatus sesseionStatus, ModelAndView model) {
        sesseionStatus.setComplete();
        model.setViewName("redirect:/holds/me");
        return model;
    }
    
    @RequestMapping(value = "me", method = RequestMethod.GET)
    public ModelAndView list(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
            ModelAndView model) {
        List<Seminar> rows = seminarService.findAllByOwner(accountUserDetails.getAccount().getId());
        model.addObject("rows", rows);
        model.setViewName("managehold/list");
        return model;
    }
    
    @GetMapping(value = {"basic/input", "{seminarId}/basic/input"})
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView inputBasic(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
            @PathVariable(name = "seminarId", required = false) Long seminarId,
            ManageHoldBasicForm form,
            ModelAndView model) {
        if (seminarId != null) {
            Seminar seminar = seminarService.findOneByOwner(seminarId, accountUserDetails.getAccount().getId());
            if (seminar == null) {
                model.setViewName("redirect:/");
                return model;
            }
            form.set(seminar);
            if (!form.getPlaceSupported()) {
                model.addObject("cities", cityService.findByPrefectureId(form.getPrefectureId()));
            }
        }
        form.setId(seminarId);
        model.setViewName("managehold/basicForm");
        return model;
    }
    
    @PostMapping(value = {"basic/save", "{seminarId}/basic/save"})
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView saveBasic(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
            @PathVariable(name = "seminarId", required = false) Long seminarId,
            @Validated ManageHoldBasicForm form, 
            BindingResult result,
            ModelAndView model) throws Exception {
        
        if (result.hasErrors()) {
            if (!form.getPlaceSupported()) {
                model.addObject("cities", cityService.findByPrefectureId(form.getPrefectureId()));
            }
            model.setViewName("managehold/basicForm");
            return model;
        }
        Seminar seminar = new Seminar(seminarId,
                                    ProviderId.SEMIPRO,
                                    form.getTitle(),
                                    SeminarType.valueOf(form.getSeminarType()),
                                    LocalDateTime.of(LocalDate.parse(form.getStartingDate()), LocalTime.parse(form.getStartingTime())), 
                                    LocalDateTime.of(LocalDate.parse(form.getEndingDate()), LocalTime.parse(form.getEndingTime())),
                                    form.getPlaceSupported(),
                                    form.getPrefectureId(),
                                    form.getCityId(),
                                    form.getAddress(),
                                    form.getPlace());
        seminar = seminarService.save(seminar, accountUserDetails.getAccount());
        
        model.setViewName("redirect:/holds/" + seminar.getId() + "/advanced/input");
        return model;
    }
    
    @GetMapping(value="{seminarId}/advanced/input")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView inputAdvanced(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
                @PathVariable("seminarId") Long seminarId,
                ManageHoldAdvancedForm form,
                ModelAndView model) {
        
        form.set(seminarService.findOneByOwner(seminarId, accountUserDetails.getAccount().getId()));
        model.addObject("seminarId", seminarId);
        model.addObject("s3Url", s3StorageService.getUrl());
        model.setViewName("managehold/advancedForm");
        return model;
    }
    
    @PostMapping(value = "{seminarId}/advanced/save")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView saveAdvanced(@AuthenticationPrincipal AccountUserDetails account,
            @PathVariable("seminarId") String seminarId,
            @Validated ManageHoldAdvancedForm form, 
            BindingResult result,
            ModelAndView model) throws IOException{
        
        if (result.hasErrors()) {
            model.addObject("seminarId", seminarId);
            model.setViewName("managehold/advancedForm");
            return model;
        }
        manageHoldHelper.saveAdvanced(Long.valueOf(seminarId), form);
        model.setViewName("redirect:/holds/" + seminarId + "/ticket/input");
        return model;
    }
    
    @GetMapping(value="{seminarId}/ticket/input")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    public ModelAndView inputTicket(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
                @PathVariable("seminarId") Long seminarId,
                ManageHoldTicketForm form,
                ModelAndView model) {
        
        form.set(seminarService.findOneByOwner(seminarId, accountUserDetails.getAccount().getId()));
        model.addObject("seminarId", seminarId);
        model.setViewName("managehold/ticketForm");
        return model;
    }
    
    @PostMapping(value = "{seminarId}/ticket/save")
    @TransactionTokenCheck(value = "create", type = TransactionTokenType.IN)
    public ModelAndView saveTicket(@AuthenticationPrincipal AccountUserDetails account,
            @PathVariable("seminarId") String seminarId,
            @Validated ManageHoldTicketForm form, 
            BindingResult result,
            ModelAndView model) throws IOException{
        
        if (result.hasErrors()) {
            model.addObject("seminarId", seminarId);
            model.setViewName("managehold/ticketForm");
            return model;
        }
        manageHoldHelper.saveTicket(Long.valueOf(seminarId), form);
        model.setViewName("redirect:/seminars/" + seminarId + "/reserve");
        return model;
    }
    
    @GetMapping(value="{seminarId}/publish")
    public ModelAndView publish(@AuthenticationPrincipal AccountUserDetails accountUserDetails,
            @PathVariable("seminarId") Long seminarId,
                ModelAndView model) {
        
        seminarService.publish(seminarId, accountUserDetails.getAccount());
        model.setViewName("redirect:/seminars/" + seminarId + "/reserve");
        return model;
    }
}
