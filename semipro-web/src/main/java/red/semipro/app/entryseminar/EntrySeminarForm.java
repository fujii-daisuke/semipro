package red.semipro.app.entryseminar;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * セミナーチケット - form
 */
@Data
@Builder
public class EntrySeminarForm implements Serializable {

    private static final long serialVersionUID = -3483045843199265872L;

    @NotNull
    private String selectedStripeCustomerCardId;
}
