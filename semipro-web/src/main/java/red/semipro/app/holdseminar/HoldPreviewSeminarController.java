package red.semipro.app.holdseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.domain.service.seminar.SeminarService;

@Controller
@RequestMapping("holds/preview")
public class HoldPreviewSeminarController {

    @Autowired
    private SeminarService seminarService;
    
    @GetMapping(value="{seminarId}")
    public ModelAndView input(@PathVariable("seminarId") String seminarId,
                ModelAndView model) {
        
        model.addObject("seminar", seminarService.findOneWithDetails(Long.valueOf(seminarId)));
        model.setViewName("holdseminar/preview/show");
        return model;
    }
}
