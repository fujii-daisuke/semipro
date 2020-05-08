package red.semipro.app.mypage.editseminar.overview;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import red.semipro.common.validator.upload.UploadFileMaxSize;
import red.semipro.common.validator.upload.UploadFileNotEmpty;

/**
 * セミナー概要作成 - form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditSeminarOverviewForm implements Serializable {

    private static final long serialVersionUID = -8399888393916035148L;

    @NotNull
    private Long seminarId;

    @NotNull
    @Size(max = 40)
    private String title;

    @Size(max = 150)
    private String summary;

    @Size(max = 1000)
    private String lecturerProfile;

//    @UploadFileRequired
    @UploadFileNotEmpty
    @UploadFileMaxSize(value = 10485760)
    private MultipartFile mainImage;

    private String mainImageUrl;
}
