package red.semipro.domain.model.seminar;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.account.Account;

/**
 * セミナー - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seminar implements Serializable {

    private static final long serialVersionUID = 8466295299654964866L;

    /**
     * セミナーID
     */
    private Long id;

    /**
     * 公開ステータス
     */
    private OpeningStatus openingStatus;

    /**
     * 投稿アカウント
     */
    private Account account;

    /**
     * セミナー
     */
    @Valid
    private SeminarGoal goal;

    /**
     * セミナー概要
     */
    @Valid
    private SeminarOverview overview;

    /**
     * セミナー内容
     */
    @Valid
    private SeminarContents contents;

    /**
     * セミナーチケット
     */
    @Valid
    private List<SeminarTicket> ticketList;

    /**
     * 申し込み者数
     */
    private SeminarEntrySummary entrySummary;

    /**
     * メイン画像Url
     */
    private String mainImageUrl;

}
