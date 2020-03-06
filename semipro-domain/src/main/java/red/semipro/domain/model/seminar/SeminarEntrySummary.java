package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーエントリーサマリ - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarEntrySummary implements Serializable {

    private static final long serialVersionUID = -7711015161092314499L;

    /**
     * セミナーID
     */
    private Long seminarId;

    /**
     * 申し込み者数
     */
    private Integer entryCount;
}
