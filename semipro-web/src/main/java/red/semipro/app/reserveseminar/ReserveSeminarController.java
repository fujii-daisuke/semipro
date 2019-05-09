package red.semipro.app.reserveseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.domain.service.seminar.SeminarServiceImpl;

@Controller
@RequestMapping(value = "seminars")
public class ReserveSeminarController {

    @Autowired
    SeminarServiceImpl seminarService;
    
    @GetMapping(value = "{seminarId}/reserve")
    public ModelAndView detail(@PathVariable("seminarId") Long seminarId,
        ModelAndView model) {
        
        model.addObject("seminar", seminarService.findOneWithDetails(seminarId));
        model.setViewName("detailseminar/reserveForm");
        return model;
    }
}
