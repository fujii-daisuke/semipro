package red.semipro.domain.model.seminar;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーチケット - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarTicket implements Serializable {

    private static final long serialVersionUID = -6611579456872285721L;

    /** ID */
    @NotNull
    private Long id;

    /** セミナーID */
    @NotNull
    private Long seminarId;

    /** チケット名 */
    @NotNull
    private String name;

    /** チケット価格 */
    @NotNull
    private Long price;

    /** 定員 */
    @NotNull
    private Integer capacity;

    public boolean isFreeTicket() {
        return price == 0;
    }
}
