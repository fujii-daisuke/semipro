package red.semipro.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarTicket implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private String name;
    private Integer price;
}
