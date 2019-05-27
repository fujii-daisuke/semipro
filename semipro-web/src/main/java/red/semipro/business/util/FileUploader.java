package red.semipro.business.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader {

    public void upload(MultipartFile file, String category, String key) throws IOException {
        StringBuffer filePath = new StringBuffer("upload").append(File.separator).append(category);
        File uploadDir = new File(filePath.toString());
//        uploadDir.mkdirs();
        File uploadFile = new File(uploadDir.getPath() + File.separator + key + "." + getSuffix(file.getOriginalFilename()));
        byte[] bytes = file.getBytes();
        BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
        uploadFileStream.write(bytes);
        uploadFileStream.close();
    }
    
    /**
     * ファイル名から拡張子を返します。
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {
        if (fileName == null)
            return null;
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }
}
