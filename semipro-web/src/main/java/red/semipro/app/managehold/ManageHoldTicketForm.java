package red.semipro.app.managehold;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.model.SeminarTicket;

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
    
    public void set(Seminar seminar) {
        if (seminar.getTickets() != null) {
            this.tickets = new ArrayList<Ticket>();
            for (SeminarTicket ticket: seminar.getTickets()) {
                this.tickets.add(new Ticket(ticket));
            }
        }
        this.entryStartingDate = seminar.getEntryStartingAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.entryStartingTime = seminar.getEntryStartingAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.entryEndingDate = seminar.getEntryEndingAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.entryEndingTime = seminar.getEntryEndingAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.capacity = seminar.getCapacity();
        this.minimumNumberHosts = seminar.getMinimumNumberHosts();
    }
}
