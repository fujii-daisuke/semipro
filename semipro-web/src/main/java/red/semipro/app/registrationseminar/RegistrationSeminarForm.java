package red.semipro.app.registrationseminar;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegistrationSeminarForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer seminarType;
    private String title;
    private Integer startedAtYear;
    private Integer startedAtMonth;
    private Integer startedAtDay;
    private Integer startedAtHour;
    private Integer startedAtMinute;
    private Integer endedAtYear;
    private Integer endedAtMonth;
    private Integer endedAtDay;
    private Integer endedAtHour;
    private Integer endedAtMinute;

}
