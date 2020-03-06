package red.semipro.app.createseminar.contents.image.upload;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import red.semipro.common.validator.upload.UploadFileMaxSize;
import red.semipro.common.validator.upload.UploadFileNotEmpty;
import red.semipro.common.validator.upload.UploadFileRequired;
import red.semipro.domain.enums.SeminarImageType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeminarContentsImageUploadForm {

    @NotNull
    private Long seminarId;

    @NotNull
    private SeminarImageType seminarImageType;

    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 10485760)
    private MultipartFile image;

}
