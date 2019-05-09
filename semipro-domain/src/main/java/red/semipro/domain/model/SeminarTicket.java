package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeminarTicket implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private String name;
    private Integer price;
    
    public SeminarTicket(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
