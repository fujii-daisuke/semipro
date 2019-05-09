package red.semipro.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.PaymentType;

@Data
@NoArgsConstructor
public class SeminarPaymentType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long seminarId;
    private PaymentType paymentType;
    
    public SeminarPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getTypeName() {
        return paymentType.getName();
    }
}
