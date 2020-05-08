package red.semipro.app.mypage.editseminar.ticket;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーチケット - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditSeminarTicketForm implements Serializable {

    private static final long serialVersionUID = 6028695377613358571L;

    @NotNull
    private Long seminarId;

    @Valid
    private List<Ticket> ticketList;
}
