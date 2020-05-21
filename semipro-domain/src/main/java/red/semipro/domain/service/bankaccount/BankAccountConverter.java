package red.semipro.domain.service.bankaccount;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import red.semipro.domain.enums.BusinessType;

@Component
public class BankAccountConverter {

    public BankAccount convert(@Nonnull final com.stripe.model.BankAccount bankAccount) {
        return BankAccount.builder()
            .accountHolderName(bankAccount.getAccountHolderName())
            .businessType(BusinessType.getBusinessType(bankAccount.getAccountHolderType()))
            .country(bankAccount.getCountry())
            .currency(bankAccount.getCurrency())
            .last4(bankAccount.getLast4())
            .bankCode(bankAccount.getRoutingNumber().substring(0,4))
            .branchCode(bankAccount.getRoutingNumber().substring(4))
            .build();
    }

}
