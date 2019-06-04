package red.semipro.app.managehold;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import red.semipro.common.upload.UploadFileMaxSize;
import red.semipro.common.upload.UploadFileNotEmpty;
import red.semipro.common.upload.UploadFileRequired;
@Data
public class ManageHoldAdvancedForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize
    private MultipartFile mainImage;
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
}
