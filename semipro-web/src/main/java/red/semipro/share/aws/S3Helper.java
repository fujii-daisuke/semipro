package red.semipro.share.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3 - helper
 */
@Component
@RequiredArgsConstructor
public class S3Helper {

    private final AmazonS3 amazonS3;

    @Value("${custom.aws.s3.bucket}")
    private String BUCKET;

    /**
     * S3にファイルをアップロードを行います
     *
     * @param file アップロードファイル
     * @param path アップロードパス
     * @throws java.io.IOException アップロード失敗時の例外
     */
    public void upload(@NotNull final MultipartFile file, @NotNull final String path)
        throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
//        metadata.addUserMetadata("x-amz-meta-title", FilenameUtils.getName(path));

        final PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, path,
            file.getInputStream(), metadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);
    }

    /**
     * S3からファイルを削除します
     *
     * @param path 削除ファイルパス
     */
    public void delete(@NotNull final String path) {
        amazonS3.deleteObject(BUCKET, path);
    }

    /**
     * ファイルURLを取得します
     *
     * @param path アップロードバス
     * @return URL
     */
    public String getImageUrl(@NotNull final String path) {
        return amazonS3.getUrl(BUCKET, path).toString();
    }
}
