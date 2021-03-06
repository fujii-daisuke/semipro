package red.semipro.admin.app.approveseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import red.semipro.domain.service.approve.ApproveService;

/**
 * セミナー承認 - controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/seminars")
public class ApproveSeminarController {

    private final ApproveService approveService;

    /**
     * セミナー承認を行います
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/approve")
    public ModelAndView approve(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) {

        approveService.approve(seminarId);
        model.setViewName("redirect:/seminars/" + seminarId + "/detail");
        return model;
    }

}
