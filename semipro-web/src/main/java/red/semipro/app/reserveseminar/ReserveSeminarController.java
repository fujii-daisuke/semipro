package red.semipro.app.reserveseminar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * セミナー予約 - controller
 */
@Controller
@RequestMapping(value = "seminars")
@RequiredArgsConstructor
public class ReserveSeminarController {

    private final ReserveSeminarHelper reserveSeminarHelper;

    /**
     * セミナー詳細画面を表示します
     *
     * @param seminarId セミナーID
     * @param model     ModelAndView
     * @return ModelAndView
     */
    @GetMapping(value = "{seminarId}/reserve")
    public ModelAndView detail(@PathVariable("seminarId") final Long seminarId,
        ModelAndView model) throws IllegalAccessException {

        model.addObject("seminarDetail", reserveSeminarHelper.findSeminarDetail(seminarId));
        model.setViewName("reserveseminar/reserve");
        return model;
    }
}
