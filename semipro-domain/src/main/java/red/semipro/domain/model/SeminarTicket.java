package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeminarTicket implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private String name;
    private Integer price;
    private Integer num;
    
    public SeminarTicket() {
    }
    
    public SeminarTicket(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
    
    public SeminarTicket(Long seminarId, String name, Integer price, Integer num) {
        this.seminarId = seminarId;
        this.name = name;
        this.price = price;
        this.num = num;
    }
}
