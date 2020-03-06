package red.semipro.app.createseminar.overview.image.upload;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import red.semipro.common.validator.upload.UploadFileMaxSize;
import red.semipro.common.validator.upload.UploadFileNotEmpty;
import red.semipro.common.validator.upload.UploadFileRequired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeminarOverviewImageUploadForm {

    @NotNull
    private Long seminarId;

    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 10485760)
    private MultipartFile image;

}
