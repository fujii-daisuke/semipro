package red.semipro.business.aws;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3StorageService {

    @Value("${custom.aws.s3.bucket}")
    private String bucket;
    
    @Autowired
    private AmazonS3 amazonS3;
    
    public void upload(MultipartFile file, String filename) throws IOException {
        amazonS3.putObject(bucket + "/seminar", filename, file.getInputStream(), new ObjectMetadata());
    }
    
    public String getUrl() {
        return "https://" + bucket + ".s3-ap-northeast-1.amazonaws.com/seminar/";
    }
}
