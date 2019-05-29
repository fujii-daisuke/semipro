package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class City implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Prefecture prefecture;
    private String name;
    
    public City() {
    }
    public City(Integer id) {
        this.id = id;
    }
}
