package red.semipro.domain.model.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.RegisterStatus;

/**
 * アカウント - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = -8252561124132925578L;

    /**
     * アカウントID
     */
    private Long id;

    /**
     * メールアドレス
     */
    private String email;

    /**
     * パスワード
     */
    private String password;

    /**
     * ユーザー名
     */
    private String username;

    /**
     * 登録ステータス
     */
    private RegisterStatus registerStatus;

}
