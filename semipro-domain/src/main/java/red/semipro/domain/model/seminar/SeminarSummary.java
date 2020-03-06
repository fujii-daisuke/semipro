package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.Seminar;

/**
 * セミナーサマリー - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarSummary implements Serializable {

    private static final long serialVersionUID = -2200819303641996033L;

    /** セミナー */
    private Seminar seminar;

    /** セミナー概要 */
    private SeminarOverview overview;

    /** セミナー開催地 */
    private SeminarPlace place;

    /** 申し込み者数 */
    private SeminarEntrySummary entrySummary;

}
