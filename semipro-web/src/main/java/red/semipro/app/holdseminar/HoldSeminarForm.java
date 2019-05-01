package red.semipro.app.holdseminar;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class HoldSeminarForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String title;
    @NotBlank
    private Integer seminarType;
    @NotBlank
    private String startingDate;
    @NotBlank
    private Integer startingTime;
    @NotBlank
    private Integer endingDate;
    @NotBlank
    private Integer endingTime;

}
