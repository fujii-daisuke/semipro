package red.semipro.share.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * メール環境変数
 */
@Configuration
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
