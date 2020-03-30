package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナーエントリー - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarEntry implements Serializable {

    private static final long serialVersionUID = 6460189779761271776L;

    /**
     * ID
     */
    private Long id;

    /**
     * セミナーID
     */
    private Long seminarId;

    /**
     * 申し込みアカウントID
     */
    private Long accountId;

    /**
     * 申し込みセミナーチケットID
     */
    private Long seminarTicketId;

    /**
     * Stripe顧客カードID
     */
    private String stripeCustomerCardId;

}
