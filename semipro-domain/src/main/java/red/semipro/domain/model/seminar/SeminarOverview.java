package red.semipro.domain.model.seminar;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セミナー概要 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarOverview implements Serializable {

    private static final long serialVersionUID = 3274282710215216589L;

    /**
     * セミナーID
     */
    @NotNull
    private Long seminarId;

    /**
     * タイトル
     */
    @NotNull
    private String title;

    /**
     * 概要
     */
    private String summary;

    /**
     * 講師プロフィール
     */
    private String lecturerProfile;

    /**
     * メイン画像拡張子
     */
    @NotNull
    private String mainImageExtension;

}
