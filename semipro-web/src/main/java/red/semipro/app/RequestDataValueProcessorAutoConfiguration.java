package red.semipro.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

/**
 * トークン自動埋め込み設定
 */
@Configuration
@AutoConfigureAfter(SecurityAutoConfiguration.class)
public class RequestDataValueProcessorAutoConfiguration {

    /**
     * Csrfトークン、トランザクショントークンの自動埋め込み設定を行う
     *
     * @return RequestDataValueProcessor
     */
    @Bean
    RequestDataValueProcessor requestDataValueProcessor() {
        CompositeRequestDataValueProcessor compositeRequestDataValueProcessor = new CompositeRequestDataValueProcessor(
            new CsrfRequestDataValueProcessor(),
            new TransactionTokenRequestDataValueProcessor());

        return new RequestDataValueProcessor() {

            @Override
            public String processUrl(HttpServletRequest request, String url) {
                return compositeRequestDataValueProcessor.processUrl(request, url);
            }

            @Override
            public String processFormFieldValue(HttpServletRequest request, String name,
                String value, String type) {
                return compositeRequestDataValueProcessor
                    .processFormFieldValue(request, name, value, type);
            }

            @Override
            public String processAction(HttpServletRequest request, String action,
                String httpMethod) {
                return compositeRequestDataValueProcessor
                    .processAction(request, action, httpMethod);
            }

            @Override
            public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
                return compositeRequestDataValueProcessor.getExtraHiddenFields(request);
            }
        };
    }
}
