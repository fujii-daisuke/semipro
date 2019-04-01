package red.semipro.searchseminar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarServiceImpl;

@RequestMapping(value = "/")
@Controller
public class SearchSeminarController {

    @Autowired
    SeminarServiceImpl seminarService;
    
    @ModelAttribute("seminarList")
    public List<Seminar> getSeminarList() {
        return seminarService.findAll();
    }
    
    @GetMapping
    public ModelAndView init(ModelAndView model) {
        model.setViewName("searchseminar/searchSeminar");
        return model;
    }
    
    @GetMapping(value = "checkout")
    public ModelAndView input(ModelAndView model) {
        model.setViewName("test/checkout-page");
        return model;
    }

}
