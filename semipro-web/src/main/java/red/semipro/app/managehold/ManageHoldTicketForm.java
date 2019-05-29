package red.semipro.app.managehold;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ManageHoldTicketForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Valid
    private List<Ticket> tickets;
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
