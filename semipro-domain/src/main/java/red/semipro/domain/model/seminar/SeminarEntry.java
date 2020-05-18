package red.semipro.domain.model.seminar;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.account.Account;

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
     * 申し込みアカウント
     */
    private Account account;

    /**
     * 申し込みセミナーチケット
     */
    private SeminarTicket ticket;

    /**
     * Stripe顧客カードID
     */
    private String stripeCustomerCardId;

    /**
     * StripeチャージID
     */
    private String stripeChargeId;

    /**
     * Stripe送金ID
     */
    private String stripeTransferId;
}
