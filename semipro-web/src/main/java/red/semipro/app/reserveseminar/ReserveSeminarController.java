package red.semipro.app.reserveseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import red.semipro.domain.service.userdetails.AccountUserDetails;

@Controller
@RequestMapping(value = "seminars")
public class ReserveSeminarController {

    @Autowired
    private ReserveSeminarHelper helper;

    @GetMapping(value = "{seminarId}/reserve")
    public ModelAndView detail(@AuthenticationPrincipal AccountUserDetails account,
            @PathVariable("seminarId") Long seminarId,
        ModelAndView model) {
        
        model.addObject("output", helper.findSeminarDetail(seminarId, account == null ? null:account.getMember()));
        model.setViewName("reserveseminar/reserveForm");
        return model;
    }
}
