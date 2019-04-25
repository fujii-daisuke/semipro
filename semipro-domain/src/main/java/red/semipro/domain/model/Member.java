package red.semipro.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.RegisterStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String password;
    private String username;
    private RegisterStatus registerStatus;
    
}
