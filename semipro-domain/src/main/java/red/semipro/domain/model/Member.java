package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.RegisterStatus;

@Data
@NoArgsConstructor
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String password;
    private String username;
    private RegisterStatus registerStatus;
    
    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.registerStatus = RegisterStatus.PRE;
    }
}
