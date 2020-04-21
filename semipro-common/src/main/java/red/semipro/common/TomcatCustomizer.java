package red.semipro.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Value("${tomcat.ajp.port:0}")
    private int ajpPort;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        if (ajpPort != 0) {
            factory.setProtocol("AJP/1.3");
            factory.setPort(ajpPort);
        }
    }
}
