package red.semipro.app.mypage.editseminar.ticket;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TicketPriceValidator implements ConstraintValidator<TicketPrice, Long> {

    @Override
    public void initialize(TicketPrice constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long price, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(price)) {
            return true;
        }
        return price.equals(0L) || price >= 500;
    }
}
