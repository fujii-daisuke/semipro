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
public class EventonSeminarCriteria implements Serializable {

    private static final long serialVersionUID = 542609228342097927L;

    private boolean isOpening;
}
