package red.semipro.domain.repository.seminar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.OpeningStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarCriteria implements Serializable {

    private static final long serialVersionUID = -1149497141847304264L;

    private Long id;

    private Long accountId;

    private OpeningStatus openingStatus;

    private List<OpeningStatus> openingStatusList;

    private Long seminarTicketId;

    private LocalDateTime executionDate;
}
