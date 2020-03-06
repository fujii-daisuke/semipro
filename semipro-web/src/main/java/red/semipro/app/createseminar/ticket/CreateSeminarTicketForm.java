package red.semipro.app.createseminar.ticket;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * セミナーチケット - form
 */
@Data
@Builder
public class CreateSeminarTicketForm implements Serializable {

    private static final long serialVersionUID = 6028695377613358571L;

    @NotNull
    private Long seminarId;

    @Valid
    private List<Ticket> ticketList;
}
