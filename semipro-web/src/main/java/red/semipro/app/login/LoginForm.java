package red.semipro.app.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {

    @NotNull
    @Email
    private String email;
    
    @NotNull
    @Size(min=8, max=20)
    private String password;
}
