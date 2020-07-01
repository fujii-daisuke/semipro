package red.semipro.domain.service.entry;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarTicket;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrySeminar implements Serializable {

    private static final long serialVersionUID = -2794826135965863696L;

    private Seminar seminar;

    private SeminarTicket selectedTicket;
}
