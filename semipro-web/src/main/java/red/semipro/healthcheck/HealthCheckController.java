package red.semipro.healthcheck;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "healthcheck")
@Controller
public class HealthCheckController {

    @GetMapping
    @ResponseBody
    public String healthCheck() {
        return "success";
    }
}
