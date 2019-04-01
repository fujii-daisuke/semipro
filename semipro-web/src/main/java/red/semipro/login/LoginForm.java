package red.semipro.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min=8, max=20)
    private String password;
}
