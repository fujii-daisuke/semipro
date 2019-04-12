package red.semipro.app.signup;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @NotBlank
    @Size(min=4, max=12)
    @Pattern(regexp = "[0-9a-zA-Z]+")
    private String username;

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min=8, max=20)
    private String password;
    
}
