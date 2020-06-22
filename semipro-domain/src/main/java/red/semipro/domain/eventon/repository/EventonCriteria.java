package red.semipro.domain.eventon.repository;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventonCriteria implements Serializable {

    private static final long serialVersionUID = 4488603095810819372L;

    private String keyword = "セミナー";

    private Integer start = 1;

    private Integer limit = 100;

    private String order = "started_at_desc";

}
