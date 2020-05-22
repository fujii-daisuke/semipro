package red.semipro.domain.service.email;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class EmailSharedService {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    private final EmailMessageProperty emailMessageProperty;
    private final MailSender mailSender;

    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;

    /**
     * メール送信を行います
     *
     * @param input 入力パラメーター
     */
    public void sendMail(@Nonnull final EmailInput input) {

        final Context ctx = new Context(input.getLocale());
        input.getVariableMap().forEach(ctx::setVariable);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Optional.ofNullable(input.getFromEmail()).orElse(fromEmail));
        message.setTo(input.getRecipientEmail());
        message.setSubject(
            emailMessageProperty.get(input.getEmailDocumentType().getKey() + ".subject"));
        message.setText(
            emailTemplateEngine().process("text/" + input.getEmailDocumentType().getKey(), ctx));
        if (Objects.nonNull(input.getBcc())) {
            message.setBcc(input.getBcc());
        }
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
