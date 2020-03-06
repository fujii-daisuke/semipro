package red.semipro.share.seminar;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import red.semipro.domain.enums.SeminarImageType;
import red.semipro.share.aws.S3Helper;

/**
 * セミナー画像 - helper
 */
@Component
@RequiredArgsConstructor
public class SeminarImageHelper {

    private final S3Helper s3Helper;

    @Value("${custom.aws.s3.seminar.path}")
    private String SEMINAR_PATH;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /**
     * メイン画像パスを生成します
     *
     * @param seminarId セミナーID
     * @param extension メイン画像拡張子
     * @return メイン画像パス
     */
    public String createMainImagePath(@NotNull final Long seminarId,
        @NotNull final String extension) {
        return SEMINAR_PATH + seminarId + "/main." + extension;
    }

    /**
     * メイン画像URLを返却します
     *
     * @param seminarId セミナーID
     * @param extension メイン画像拡張子
     * @return メイン画像URL
     */
    public String getMainImageUrl(@NotNull final Long seminarId,
        @NotNull final String extension) {
        return s3Helper.getImageUrl(createMainImagePath(seminarId, extension));
    }

    /**
     * ファイルアップロードを行います
     *
     * @param file ファイル
     * @param key  キー
     * @throws IOException アップロード時の例外
     */
    public void upload(@NotNull final MultipartFile file, @NotNull final String key)
        throws IOException {
        s3Helper.upload(file, key);
    }

    /**
     * コンテンツ画像パスを生成します
     *
     * @param seminarId セミナーID
     * @param extension 拡張子
     * @return ファイルパス
     */
    public String createContentsImagePath(@Nonnull final Long seminarId,
        @NotNull final String extension) {
        return SEMINAR_PATH + seminarId + "/" + SeminarImageType.CONTENTS.getValue()
            + "/" + LocalDateTime.now().format(dateTimeFormatter)
            + "." + extension;
    }

    /**
     * コンテンツ画像URLを返却します
     *
     * @param path コンテンツ画像パス
     * @return コンテンツ画像URL
     */
    public String getContentsImageUrl(@NotNull final String path) {
        return s3Helper.getImageUrl(path);
    }

    /**
     * ファイルを削除します
     *
     * @param key キー
     */
    public void delete(@NotNull final String key) {
        s3Helper.delete(key);
    }

}
