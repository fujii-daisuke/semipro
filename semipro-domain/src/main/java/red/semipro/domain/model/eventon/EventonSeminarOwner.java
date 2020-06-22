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
public class EventonSeminarOwner implements Serializable {

    private static final long serialVersionUID = 284432630319337697L;
    private Integer eventId;
    private Integer id;
    private String name;
    private String url;
}
