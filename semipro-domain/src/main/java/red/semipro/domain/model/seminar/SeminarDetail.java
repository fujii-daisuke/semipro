package red.semipro.domain.model.seminar;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.Account;
import red.semipro.domain.model.Seminar;

/**
 * セミナー詳細 - model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeminarDetail implements Serializable {

    private static final long serialVersionUID = 8466295299654964866L;

    /**
     * セミナー
     */
    @Valid
    private Seminar seminar;

    /**
     * セミナー概要
     */
    @Valid
    private SeminarOverview seminarOverview;

    /**
     * セミナー内容
     */
    @Valid
    private SeminarContents seminarContents;

    /**
     * セミナー開催地
     */
    @Valid
    private SeminarPlace seminarPlace;

    /**
     * セミナーチケット
     */
    @Valid
    private List<SeminarTicket> seminarTicketList;

    /**
     * 申し込み者数
     */
    private SeminarEntrySummary seminarEntrySummary;

    /**
     * アカウント
     */
    private Account account;

    /**
     * メイン画像Url
     */
    private String mainImageUrl;

}
