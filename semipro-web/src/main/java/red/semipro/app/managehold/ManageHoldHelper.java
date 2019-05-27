package red.semipro.app.managehold;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import red.semipro.business.util.FileUploader;
import red.semipro.domain.model.Seminar;
import red.semipro.domain.service.seminar.SeminarService;

@Component
public class ManageHoldHelper {

    @Autowired
    private SeminarService seminarService;
    @Autowired
    FileUploader fileUploader;
    
    @Transactional
    public void advancedRegistration(Seminar seminar, MultipartFile mainImage) throws IOException {
        fileUploader.upload(mainImage, "seminar", seminar.getId().toString());
        seminarService.update(seminar);
    }
}
