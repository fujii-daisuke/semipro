package red.semipro.app.signup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import red.semipro.common.Crypto;
import red.semipro.domain.model.Account;
import red.semipro.domain.service.account.AccountService;
import red.semipro.share.email.EmailDocument;
import red.semipro.share.email.EmailHelper;

/**
 * 会員登録 - helper
 */
@Component
@RequiredArgsConstructor
public class SignUpHelper {

    private final AccountService accountService;
    private final EmailHelper emailHelper;

    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;
    @Value("${custom.application.schema}")
    private String schema;
    @Value("${custom.application.domain}")
    private String domain;

    /**
     * 会員登録を行う
     *
     * @param account アカウント
     * @param locale  ロケール
     * @throws Exception 暗号化失敗
     */
    public void register(@Nonnull final Account account, @Nonnull final Locale locale)
        throws Exception {

        accountService.register(account);

        sendMail(account, locale);
    }

    /**
     * メール送信を行う
     *
     * @param account アカウント
     * @param locale  　ロケール
     * @throws Exception 暗号化失敗
     */
    private void sendMail(Account account, Locale locale) throws Exception {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("schema", schema);
        variableMap.put("domain", domain);
        variableMap.put("account", account);
        variableMap.put("activationKey", (new Crypto()).encrypto(account.getId().toString()));
        emailHelper.sendMail(
            EmailDocument.SIGN_UP,
            variableMap,
            account.getEmail(),
            fromEmail,
            locale);
    }

}
