package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;
import red.semipro.domain.enums.RegisterStatus;

@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String password;
    private String username;
    private RegisterStatus registerStatus;
    
    public Account() {
    }
    
    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.registerStatus = RegisterStatus.PRE;
    }
}
