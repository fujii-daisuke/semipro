package red.semipro.app.signup;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import red.semipro.domain.model.Account;

/**
 * 会員登録フォーム - converter
 */
@Component
@RequiredArgsConstructor
public class SignUpFormConverter {

    private final PasswordEncoder passwordEncoder;

    /**
     * 会員登録フォームをアカウントにコンバートする
     *
     * @param signUpForm 会員登録フォーム
     * @return アカウント
     */
    Account convert(@Nonnull final SignUpForm signUpForm) {
        return Account.builder()
            .email(signUpForm.getEmail())
            .username(signUpForm.getUsername())
            .password(passwordEncoder.encode(signUpForm.getPassword()))
            .build();
    }

}
