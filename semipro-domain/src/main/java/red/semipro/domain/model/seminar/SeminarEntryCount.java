package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarEntryCount implements Serializable {

    private static final long serialVersionUID = 2267812459535154624L;

    private Long seminarId;

    private Integer count;

    private String name;
}
