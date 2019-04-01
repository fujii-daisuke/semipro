package red.semipro.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.enums.PaymentType;
import red.semipro.domain.enums.ProviderId;
import red.semipro.domain.enums.SeminarType;
import red.semipro.domain.model.eventon.Event;
import red.semipro.domain.model.eventon.Owner;
import red.semipro.domain.model.eventon.Ticket;

/**
 * セミナーモデル
 * @author fujiidaisuke
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seminar implements Serializable {

    private static final long serialVersionUID = 1L;
    /** セミナーID */
    private Long id;
    /** 公開ステータス */
    private OpeningStatus openingStatus;
    /** プロバイダーID */
    private ProviderId providerId;
    /** プロバイダーセミナーID */
    private Integer providerSeminarId;
    /** セミナー種別 */
    private SeminarType seminarType;
    /** タイトル */
    private String title;
    /** 概要 */
    private String summary;
    /** 内容 */
    private String contents;
    /** 画像リンク */
    private String imagePath;
    /** 開催開始日時 */
    private LocalDateTime startedAt;
    /** 開催終了時間 */
    private LocalDateTime endedAt;
    /** 入場可能日時 */
    private LocalDateTime openedAt;
    /** 申し込み開始日時 */
    private LocalDateTime entryStartedAt;
    /** 申し込み終了日時 */
    private LocalDateTime entryEndedAt;
    /** キャンセル可能日時 */
    private LocalDateTime cancelAt;
    /** 定員 */
    private Integer capacity;
    /** プロバイダーセミナーURL */
    private String providerSeminarUrl;
    /** 参考URL */
    private String referenceUrl;
    /** 支払い方法 */
    private List<SeminarPaymentType> seminarPaymentTypes;
    /** チケット情報 */
    private List<SeminarTicket> seminarTickets;
    private String cancelPolicy;
    /** 都道府県 */
    private Prefecture prefecture;
    /** 開催場所 */
    private String address;
    /** 会場名 */
    private String place;
    /** 緯度 */
    private Double lat;
    /** 経度 */
    private Double lng;
    /** 参加者数 */
    private Integer accepted;
    /** waiting */
    private Integer waiting;
    /** 主催者情報 */
    private List<SeminarOwner> seminarOwners;
    /** 埋め込みコード */
    private String embedCode;
    /** 最低開催人数定員 */
    private Integer minimumNumberHosts;
    /** 更新日時 */
    private LocalDateTime updatedAt;
    
    /**
     * コンストラクタ
     * EventonApiより取得したデータからセミナーモデルを構築します
     * @param event
     */
    public Seminar(Event event) {
        this.providerId = ProviderId.EVENTON;
        this.providerSeminarId = event.getEvent_id();
        this.seminarType = SeminarType.OFFLINE;
        this.title = event.getTitle();
        this.summary = event.getSummary();
        this.contents = event.getContents();
        this.imagePath = event.getImage_path();
        if (!StringUtils.isEmpty(event.getStarted_at())) {
            this.startedAt = LocalDateTime.parse(event.getStarted_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEnded_at())) {
            this.endedAt = LocalDateTime.parse(event.getEnded_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getOpened_at())) {
            this.openedAt = LocalDateTime.parse(event.getOpened_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEntry_started_at())) {
            this.entryStartedAt = LocalDateTime.parse(event.getEntry_started_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEntry_ended_at())) {
            this.entryEndedAt = LocalDateTime.parse(event.getEntry_ended_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getCancel_at())) {
            this.cancelAt = LocalDateTime.parse(event.getCancel_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        this.capacity = event.getCapacity();
        this.providerSeminarUrl = event.getEvent_url();
        this.referenceUrl = event.getUrl();
        if (event.getPayment_types() != null && event.getPayment_types().size() > 0) {
            this.seminarPaymentTypes = new ArrayList<SeminarPaymentType>();
            for (String paymentTypeName: event.getPayment_types()) {
                PaymentType paymentType = PaymentType.nameOf(paymentTypeName);
                if (paymentType != null) {
                    this.seminarPaymentTypes.add(SeminarPaymentType.builder().paymentType(paymentType).build());
                }
            }
        }
        if (event.getTickets() != null && event.getTickets().size() > 0) {
            this.seminarTickets = new ArrayList<SeminarTicket>();
            for (Ticket ticket: event.getTickets()) {
                this.seminarTickets.add(SeminarTicket.builder().name(ticket.getName()).price(ticket.getPrice()).build());
            }
        }
        
        this.cancelPolicy = event.getCancel_policy();
        if (event.getPrefecture_id() != null) {
            this.prefecture = Prefecture.builder().id(event.getPrefecture_id()).build();
        }
        this.address = event.getAddress();
        this.place = event.getPlace();
        this.lat = event.getLat();
        this.lng = event.getLng();
        this.accepted = event.getAccepted();
        this.waiting = event.getWaiting();
        if (event.getOwners() != null && event.getOwners().size() > 0) {
            this.seminarOwners = new ArrayList<>();
            for (Owner owner: event.getOwners()) {
                this.seminarOwners.add(SeminarOwner.builder()
                                .providerOwnerId(owner.getId())
                                .name(owner.getName())
                                .url(owner.getUrl()).build());
            }
        }
        this.embedCode = event.getEmbed_code();
        this.updatedAt = LocalDateTime.parse(event.getUpdated_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    
    /**
     * 残席を取得します
     * @return 残席数
     */
    public Integer getRemaining() {
        return this.capacity - this.accepted;
    }
    
    /**
     * 受付中か判定します
     * @return 受付中: true / false
     */
    public boolean isAccepting() {
        if (this.entryStartedAt != null) {
            return LocalDateTime.now().isAfter(this.entryStartedAt) && this.entryEndedAt.isAfter(LocalDateTime.now());
        }
        return this.entryEndedAt.isBefore(LocalDateTime.now());
    }
    
    public String remainingStatus() {
        int remain = this.capacity - this.accepted;
        if (remain < 10) {
            return "空席わずか";
        } else {
            return "空席あり";
        }
    }
}
