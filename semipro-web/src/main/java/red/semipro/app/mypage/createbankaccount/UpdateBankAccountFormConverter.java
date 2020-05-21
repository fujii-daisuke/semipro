package red.semipro.app.mypage.createbankaccount;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import red.semipro.domain.service.bankaccount.BankAccount;

@Component
public class UpdateBankAccountFormConverter {

    public BankAccount convert(@Nonnull final UpdateBankAccountForm form) {
        return BankAccount.builder()
            .businessType(form.getBusinessType())
            .accountHolderName(form.getAccountHolderName())
            .build();
    }

}
