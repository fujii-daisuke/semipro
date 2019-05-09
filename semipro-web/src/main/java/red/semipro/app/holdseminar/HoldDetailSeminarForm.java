package red.semipro.app.holdseminar;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class HoldDetailSeminarForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull
    private String summary;
    @NotNull
    private String contents;
    @NotNull
    private String entryStartingDate;
    @NotNull
    private String entryStartingTime;
    @NotNull
    private String entryEndingDate;
    @NotNull
    private String entryEndingTime;
    @NotNull
    private Integer capacity;
    @NotNull
    private Integer minimumNumberHosts;
}
