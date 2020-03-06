package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.OpeningStatus;

/**
 * セミナー検索条件 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarSearchCriteria implements Serializable {

    private static final long serialVersionUID = -4817018217321740139L;

    private boolean beforeEntryEndingAt;

    private OpeningStatus openingStatus;

    private Long accountId;
}
