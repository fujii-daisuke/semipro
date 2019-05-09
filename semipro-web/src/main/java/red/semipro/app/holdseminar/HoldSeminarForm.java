package red.semipro.app.holdseminar;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class HoldSeminarForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max=50)
    private String title;
    @NotNull
    private Integer seminarType;
    @NotNull
    private String startingDate;
    @NotNull
    private String startingTime;
    @NotNull
    private String endingDate;
    @NotNull
    private String endingTime;

}
