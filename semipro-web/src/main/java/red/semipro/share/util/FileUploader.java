package red.semipro.share.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルアップロード - component
 */
@Component
public class FileUploader {

    /**
     * ファイルアップロードを行います
     *
     * @param file     ファイル
     * @param category カテゴリ
     * @param key      キー
     * @throws IOException ファイル操作時
     */
    public void upload(MultipartFile file, String category, String key)
        throws IOException {
        File uploadDir = new File("upload" + File.separator + category);
        File uploadFile =
            new File(uploadDir.getPath()
                + File.separator + key
                + "."
                + getSuffix(file.getOriginalFilename()));
        byte[] bytes = file.getBytes();
        BufferedOutputStream uploadFileStream = new BufferedOutputStream(
            new FileOutputStream(uploadFile));
        uploadFileStream.write(bytes);
        uploadFileStream.close();
    }

    /**
     * ファイル名から拡張子を返します。
     *
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }
}
