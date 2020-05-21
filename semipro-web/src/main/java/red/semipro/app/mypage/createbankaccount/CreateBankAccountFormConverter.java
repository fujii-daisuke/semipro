package red.semipro.app.mypage.createbankaccount;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import red.semipro.domain.service.bankaccount.BankAccount;

@Component
public class CreateBankAccountFormConverter {

    public CreateBankAccountForm convert(@Nonnull final BankAccount bankAccount) {
        return CreateBankAccountForm.builder()
            .businessType(bankAccount.getBusinessType())
            .bankCode(bankAccount.getBankCode())
            .branchCode(bankAccount.getBranchCode())
            .last4(bankAccount.getLast4())
            .accountHolderName(bankAccount.getAccountHolderName())
            .accountNumber(bankAccount.getAccountNumber())
            .build();
    }
}
