package red.semipro.app.managehold;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import red.semipro.domain.model.Seminar;

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
    private Boolean placeSupported;
    private Integer prefectureId;
    private Integer cityId;
    @Size(max=50)
    private String address;
    @Size(max=20)
    private String place;

    public void set(Seminar seminar) {
        this.title = seminar.getTitle();
        this.seminarType = seminar.getSeminarType().getValue();
        this.startingDate = seminar.getStartingAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.startingTime = seminar.getStartingAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endingDate = seminar.getEndingAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endingTime = seminar.getEndingAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.placeSupported = seminar.isPlaceSupported();
        if (!this.placeSupported) {
            this.prefectureId = seminar.getPrefecture().getId();
            this.cityId = seminar.getCity().getId();
            this.address = seminar.getAddress();
            this.place = seminar.getPlace();
        }
    }
}
