package red.semipro.domain.service.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import red.semipro.common.Crypto;
import red.semipro.domain.common.constants.MessageId;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.repository.account.AccountRepository;
import red.semipro.domain.service.email.EmailDocumentType;
import red.semipro.domain.service.email.EmailInput;
import red.semipro.domain.service.email.EmailSharedService;

/**
 * アカウント - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final EmailSharedService emailSharedService;

    @Value("${custom.application.schema}")
    private String schema;
    @Value("${custom.application.domain}")
    private String domain;

    /**
     * メールアドレスが登録済みか判定します
     *
     * @param email          メールアドレス
     * @param registerStatus 登録ステータス
     * @return boolean true 存在する / false 存在しない
     */
    public boolean isExistsEmail(@Nonnull final String email,
        @Nullable final RegisterStatus registerStatus) {
        return Objects.nonNull(accountRepository.findByEmail(email, registerStatus));
    }

    /**
     * ユーザー名が登録済みか判定します
     *
     * @param username       ユーザー名
     * @param registerStatus 登録ステータス
     * @return boolean true 存在する / false 存在しない
     */
    public boolean isExistsUsername(@Nonnull final String username,
        @Nullable final RegisterStatus registerStatus) {
        return Objects.nonNull(accountRepository.findByUsername(username, registerStatus));
    }

    /**
     * アカウントを登録します
     *
     * @param account アカウント
     * @return アカウント
     */
    public Account provisionalRegister(@Nonnull final Account account) {

        accountRepository.insert(account);

        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("schema", schema);
        variableMap.put("domain", domain);
        variableMap.put("account", account);
        variableMap.put("activationKey", (new Crypto()).encrypto(account.getId().toString()));

        emailSharedService.sendMail(EmailInput.builder()
            .emailDocumentType(EmailDocumentType.ACTIVATION)
            .variableMap(variableMap)
            .recipientEmail(account.getEmail())
            .locale(LocaleContextHolder.getLocale())
            .build());

        return account;
    }

    public Account activate(@Nonnull final String activationKey) {

        Crypto crypto = new Crypto();
        String accountId = crypto.decrypto(activationKey);
        Account account = accountRepository.findOne(Long.valueOf(accountId));

        if (Objects.isNull(account)
            || !RegisterStatus.PROVISIONAL.equals(account.getRegisterStatus())) {

            ResultMessages message = ResultMessages.error().add(MessageId.E_SP_FW_0500);
            throw new BusinessException(message);
        }

        accountRepository.updateRegisterStatus(account.getId(), RegisterStatus.REGULAR);

        emailSharedService.sendMail(EmailInput.builder()
            .emailDocumentType(EmailDocumentType.ACTIVATED)
            .variableMap(Map.of("account", account))
            .recipientEmail(account.getEmail())
            .locale(LocaleContextHolder.getLocale())
            .build());

        return account;
    }
}
