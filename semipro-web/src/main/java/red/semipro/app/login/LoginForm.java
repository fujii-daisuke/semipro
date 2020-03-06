package red.semipro.app.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * ログイン - form
 */
@Data
public class LoginForm {

    /**
     * メールアドレス
     */
    @NotNull
    @Email
    private String email;

    /**
     * パスワード
     */
    @NotNull
    @Size(min = 8, max = 20)
    private String password;
}
