package red.semipro.app.reserveseminar;

import lombok.Data;
import red.semipro.domain.model.Member;
import red.semipro.domain.model.Seminar;

@Data
public class SeminarDetailOutput {

    private Seminar seminar;
    
    private String mainImagePath;
    
    private Member member;
    
    public boolean isOwnership() {
        if (member == null) {
            return false;
        }
        return seminar.isOwnership(member.getId());
    }
}
