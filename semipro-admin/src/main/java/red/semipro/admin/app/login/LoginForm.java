package red.semipro.admin.app.login;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * ログイン - form
 */
@Data
public class LoginForm {

    /**
     * ユーザー名
     */
    @NotNull
    private String username;

    /**
     * パスワード
     */
    @NotNull
    private String password;
}
