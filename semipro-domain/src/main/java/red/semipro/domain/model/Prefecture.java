package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Prefecture implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    
    public Prefecture(Integer id) {
        this.id = id;
    }
}
