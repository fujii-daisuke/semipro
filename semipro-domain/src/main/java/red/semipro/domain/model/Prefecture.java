package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Prefecture implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    
    public Prefecture() {
    }
    
    public Prefecture(Integer id) {
        this.id = id;
    }
}
