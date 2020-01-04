package red.semipro.app.reserveseminar;

import lombok.Data;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;

@Data
public class SeminarDetailOutput {

    private Seminar seminar;
    
    private String mainImagePath;
    
    private Account account;
    
    public boolean isOwnership() {
        if (account == null) {
            return false;
        }
        return seminar.isOwnership(account.getId());
    }
}
