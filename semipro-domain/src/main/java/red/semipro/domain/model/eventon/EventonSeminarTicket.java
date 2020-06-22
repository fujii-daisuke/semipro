package red.semipro.domain.model.eventon;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventonSeminarTicket implements Serializable {

    private static final long serialVersionUID = -2406239335227585300L;

    private Integer eventId;
    private String name;
    private Integer price;
}
