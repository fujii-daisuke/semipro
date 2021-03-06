package red.semipro.app.mypage.editseminar.ticket;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {

    private static final long serialVersionUID = 6171173431633839901L;

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    @Min(value = 0)
    private Long price;

    @NotNull
    @Min(value = 1)
    private Integer capacity;
}
