package red.semipro.share.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3 - helper
 */
@Component
@RequiredArgsConstructor
@Slf4j
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
    public void upload(@Nonnull final MultipartFile file, @Nonnull final String path)
        throws IOException {

//        EnvironmentVariableCredentialsProvider credentials = new EnvironmentVariableCredentialsProvider();
//        log.debug("accessKeyId: " + credentials.getCredentials().getAWSAccessKeyId());
//        log.debug("secretKey: " + credentials.getCredentials().getAWSSecretKey());
//        log.debug("region: " + amazonS3.getRegionName());
//        log.debug("path: " + path);
//        if (amazonS3.doesBucketExistV2(BUCKET)) {
//            log.debug("bucket: " + BUCKET);
//        } else {
//            log.debug("bucket NG");
//        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/text");
        metadata.setContentLength(IOUtils.toByteArray(file.getInputStream()).length);
//        metadata.addUserMetadata("x-amz-meta-title", FilenameUtils.getName(path));

        final PutObjectRequest putObjectRequest =
            new PutObjectRequest(BUCKET, path, file.getInputStream(), metadata);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);
    }

    /**
     * S3からファイルを削除します
     *
     * @param path 削除ファイルパス
     */
    public void delete(@Nonnull final String path) {
        amazonS3.deleteObject(BUCKET, path);
    }

    /**
     * ファイルURLを取得します
     *
     * @param path アップロードバス
     * @return URL
     */
    public String getImageUrl(@Nonnull final String path) {
        return amazonS3.getUrl(BUCKET, path).toString();
    }
}
