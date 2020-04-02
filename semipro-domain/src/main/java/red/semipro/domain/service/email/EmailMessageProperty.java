package red.semipro.domain.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * メール環境変数
 */
@Component
@RequiredArgsConstructor
@PropertySource("classpath:/mail/MailMessages.properties")
public class EmailMessageProperty {

    private final Environment environment;

    /**
     * 環境変数を取得します
     *
     * @param key キー
     * @return 環境変数値
     */
    public String get(String key) {
        return environment.getProperty(key);
    }
}
