package red.semipro.share.healthcheck;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ヘルスチェック - controller
 */
@RequestMapping(value = "healthcheck")
@Controller
public class HealthCheckController {

    /**
     * ヘルスチェックを行います
     *
     * @return success
     */
    @GetMapping
    @ResponseBody
    public String healthCheck() {
        return "success";
    }
}
