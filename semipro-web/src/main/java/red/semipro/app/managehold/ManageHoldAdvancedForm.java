package red.semipro.app.managehold;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import red.semipro.common.upload.UploadFileMaxSize;
import red.semipro.common.upload.UploadFileNotEmpty;
import red.semipro.common.upload.UploadFileRequired;
import red.semipro.domain.model.Seminar;
@Data
public class ManageHoldAdvancedForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value=10485760)
    private MultipartFile mainImage;
    private String imagePath;
    @NotNull
    @Size(max=200)
    private String summary;
    @NotNull
    @Size(max=10000)
    private String contents;
    @NotNull
    private Boolean shootingSupported;
    @NotNull
    private Boolean shootingEditSupported;
    @NotNull
    private Boolean movieSalesSupported;
    
    public void set(Seminar seminar) {
        this.imagePath = seminar.getImagePath();
        this.summary = seminar.getSummary();
        this.contents = seminar.getContents();
        this.shootingSupported = seminar.isShootingSupported();
        this.shootingEditSupported= seminar.isShootingEditSupported();
        this.movieSalesSupported= seminar.isMovieSalesSupported();
    }
}
