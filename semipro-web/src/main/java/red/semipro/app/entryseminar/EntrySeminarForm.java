package red.semipro.app.entryseminar;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーチケット - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
    public class EntrySeminarForm implements Serializable {

        private static final long serialVersionUID = -3483045843199265872L;

        @NotNull
    private String selectedStripeCustomerCardId;
}
