package red.semipro.domain.helper.stripe.customercard;


import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCard implements Serializable {

    private static final long serialVersionUID = -8464178516740984736L;

    private String id;

    private String brand;

    private String expMonth;

    private String expYear;

    private String last4;
}
