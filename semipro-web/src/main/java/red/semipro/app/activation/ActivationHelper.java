package red.semipro.app.activation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import red.semipro.common.Crypto;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Account;
import red.semipro.domain.service.account.AccountService;
import red.semipro.share.email.EmailDocument;
import red.semipro.share.email.EmailHelper;

/**
 * アクティベーション - helper
 */
@Component
@RequiredArgsConstructor
public class ActivationHelper {

    private final AccountService accountService;
    private final EmailHelper emailHelper;

    @Value("${custom.application.email.fromEmail}")
    private String fromEmail;

    /**
     * アクティベーションを行います
     *
     * @param activationKey アクティベーションキー
     * @param locale        Locale
     * @return Account
     * @throws Exception 復号化失敗時に発生します
     */
    public Account activate(@Nonnull final String activationKey, @Nonnull final Locale locale)
        throws Exception {
        Crypto crypto = new Crypto();
        String accountId = crypto.decrypto(activationKey);
        Account account = accountService.findOne(Long.valueOf(accountId));
        if (Objects.isNull(account) || !RegisterStatus.PROVISIONAL.equals(account.getRegisterStatus())) {
            throw new IllegalStateException("account is null or register status not pre.");
        }
        accountService.updateRegisterStatus(account.getId(), RegisterStatus.REGULAR);
        sendMail(account, locale);
        return account;
    }

    /**
     * メール送信を行います
     *
     * @param account アカウント
     * @param locale  Locale
     */
    private void sendMail(Account account, Locale locale) {
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("account", account);
        emailHelper.sendMail(
            EmailDocument.ACTIVATE,
            variableMap,
            account.getEmail(),
            fromEmail,
            locale);
    }
}
