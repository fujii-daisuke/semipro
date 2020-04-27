package red.semipro.app.signup;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * 会員登録 - form
 */
@Data
public class SignUpForm implements Serializable {

    private static final long serialVersionUID = -6435111177742815808L;

    /**
     * ユーザー名
     */
    @NotNull
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[0-9a-zA-Z-]+")
    private String username;

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
