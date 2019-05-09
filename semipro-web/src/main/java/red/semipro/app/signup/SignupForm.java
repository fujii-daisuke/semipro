package red.semipro.app.signup;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @NotNull
    @Size(min=4, max=12)
    @Pattern(regexp = "[0-9a-zA-Z-]+")
    private String username;

    @NotNull
    @Email
    private String email;
    
    @NotNull
    @Size(min=8, max=20)
    private String password;
    
}
