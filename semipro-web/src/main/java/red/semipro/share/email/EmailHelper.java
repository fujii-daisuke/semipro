package red.semipro.share.email;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * メール送信 - helper
 */
@Service
@RequiredArgsConstructor
public class EmailHelper {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    private final EmailMessageProperty emailMessageProperty;
    private final MailSender mailSender;

    /**
     * メール送信を行います
     *
     * @param emailDocument  メール種類
     * @param variableMap    埋め込み変数
     * @param recipientEmail 　送信先メールアドレス
     * @param fromEmail      　送信元メールアドレス
     * @param locale         ロケール
     */
    public void sendMail(
        final EmailDocument emailDocument,
        final Map<String, Object> variableMap,
        final String recipientEmail,
        final String fromEmail,
        final Locale locale) {

        final Context ctx = new Context(locale);
        variableMap.forEach(ctx::setVariable);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(recipientEmail);
        message.setSubject(emailMessageProperty.get(emailDocument.getKey() + ".subject"));
        message.setText(emailTemplateEngine().process("text/" + emailDocument.getKey(), ctx));

        this.mailSender.send(message);
    }

    /**
     * テンプレートエンジンを取得します
     *
     * @return テンプレートエンジン
     */
    private TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(textTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    /**
     * メッセージソースを取得します
     *
     * @return メッセージソース
     */
    private ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/MailMessages");
        return messageSource;
    }

    /**
     * テンプレートリゾルバーを取得します
     *
     * @return テンプレートリゾルバー
     */
    private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
