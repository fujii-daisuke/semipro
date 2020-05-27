package red.semipro.domain.service.seminar;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.seminar.Seminar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarDetailOutput implements Serializable {

    private static final long serialVersionUID = -908323554278906513L;

    private Seminar seminar;

    private List<Long> enteredTicketIds;

    public boolean isEntered(@Nonnull final Long seminarTicketId) {
        return Objects.nonNull(enteredTicketIds) && enteredTicketIds.contains(seminarTicketId);
    }
}
