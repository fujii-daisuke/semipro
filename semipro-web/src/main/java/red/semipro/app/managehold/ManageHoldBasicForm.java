package red.semipro.app.managehold;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ManageHoldBasicForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
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
    @NotNull
    private Boolean placeSupported;
    private Integer prefectureId;
    private Integer cityId;
    private String address;
    private String place;

}
