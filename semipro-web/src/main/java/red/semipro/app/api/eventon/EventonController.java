package red.semipro.app.api.eventon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/eventon")
public class EventonController {
    
    @Autowired
    private EventonApi eventonApi;
    
    @GetMapping("/task/run")
    @ResponseBody
    public String init(ModelAndView model) {
        eventonApi.execute();
        return "success";
    }
}
