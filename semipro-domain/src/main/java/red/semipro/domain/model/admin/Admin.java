package red.semipro.domain.model.admin;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理者 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {

    private static final long serialVersionUID = 6449974035356185714L;

    /**
     * ID
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
}
