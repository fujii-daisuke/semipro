package red.semipro.common;

import java.util.Base64;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class MdcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            private Random random = new Random();
            private static final String MDC_KEY_REQUEST_ID = "request_id";
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
                String requestId = request.getHeader("unique_id");
                if (requestId == null) {
                    requestId = generateRequestId();
                }
                MDC.put(MDC_KEY_REQUEST_ID, requestId);
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) throws Exception {
                MDC.remove(MDC_KEY_REQUEST_ID);
            }

            private String generateRequestId() {
                byte[] bytes = new byte[18];
                random.nextBytes(bytes);
                return Base64.getEncoder().encodeToString(bytes);
            }
        }).addPathPatterns("/*");
    }
}
