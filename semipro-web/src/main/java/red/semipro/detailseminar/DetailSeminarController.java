package red.semipro.detailseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.domain.service.seminar.SeminarServiceImpl;

@Controller
@RequestMapping(value = "seminars")
public class DetailSeminarController {

    @Autowired
    SeminarServiceImpl seminarService;
    
    @GetMapping(value = "{seminarId}/detail")
    public ModelAndView detail(@PathVariable("seminarId") Long seminarId,
        ModelAndView model) {
        
        model.addObject("seminar", seminarService.findOneWithDetails(seminarId));
        model.setViewName("detailseminar/detail");
        return model;
    }
}
