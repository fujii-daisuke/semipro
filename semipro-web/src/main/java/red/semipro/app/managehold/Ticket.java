package red.semipro.app.managehold;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Ticket implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer num;
    

}
