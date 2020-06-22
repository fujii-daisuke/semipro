package red.semipro.domain.service.seminar;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.eventon.EventonSeminar;
import red.semipro.domain.model.seminar.Seminar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarSearchOutput implements Serializable {

    private static final long serialVersionUID = 2620396250478205438L;

    private Seminar seminar;

    private EventonSeminar eventonSeminar;

    public boolean isSemipro() {
        return Objects.nonNull(seminar);
    }
}
