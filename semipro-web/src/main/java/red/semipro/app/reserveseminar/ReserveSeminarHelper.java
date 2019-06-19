package red.semipro.app.reserveseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import red.semipro.business.aws.S3StorageService;
import red.semipro.domain.enums.ProviderId;
import red.semipro.domain.model.Member;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarServiceImpl;

@Component
public class ReserveSeminarHelper {

    @Autowired
    SeminarServiceImpl seminarService;
    @Autowired
    private S3StorageService s3StorageService;

    public SeminarDetailOutput findSeminarDetail(Long seminarId, Member member) {
        Seminar seminar = seminarService.findOneWithDetails(seminarId);
        
        SeminarDetailOutput output = new SeminarDetailOutput();
        output.setMember(member);
        output.setSeminar(seminar);
        if (ProviderId.SEMIPRO.equals(seminar.getProviderId())) {
            output.setMainImagePath(s3StorageService.getUrl() + seminar.getImagePath());
        } else {
            output.setMainImagePath(seminar.getImagePath());
        }
        return output;
    }
}
