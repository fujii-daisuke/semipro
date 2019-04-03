package red.semipro.searchseminar;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.common.PageWrapper;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.repository.seminar.SeminarSearchCriteria;
import red.semipro.domain.service.seminar.SeminarServiceImpl;

@RequestMapping(value = "/")
@Controller
public class SearchSeminarController {

    @Autowired
    private SeminarServiceImpl seminarService;
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    public ModelAndView search(SearchSeminarForm form,
            BindingResult result,
            @PageableDefault(size = 20)
            @SortDefaults(
                    {
                        @SortDefault(
                                     sort = "seminar.started_at",
                                     direction = Direction.ASC
                                    )
                    }) Pageable pageable,
            ModelAndView model) {
        
        SeminarSearchCriteria criteria= modelMapper.map(form, SeminarSearchCriteria.class);
        Page<Seminar> seminarPage = seminarService.searchSeminar(criteria, pageable);
        PageWrapper<Seminar> page = new PageWrapper<Seminar>(seminarPage, "/");
                
        model.addObject("page", page);
        model.setViewName("searchseminar/searchSeminar");
        return model;
    }

}
