package red.semipro.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import lombok.Data;
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
    private LocalDateTime startingAt;
    /** 開催終了時間 */
    private LocalDateTime endingAt;
    /** 入場可能日時 */
    private LocalDateTime openingAt;
    /** 申し込み開始日時 */
    private LocalDateTime entryStartingAt;
    /** 申し込み終了日時 */
    private LocalDateTime entryEndingAt;
    /** キャンセル可能日時 */
    private LocalDateTime cancelAt;
    /** 定員 */
    private Integer capacity;
    /** プロバイダーセミナーURL */
    private String providerSeminarUrl;
    /** 参考URL */
    private String referenceUrl;
    /** 支払い方法 */
    private List<SeminarPaymentType> paymentTypes;
    /** チケット情報 */
    private List<SeminarTicket> tickets;
    private String cancelPolicy;
    /** 都道府県 */
    private Prefecture prefecture;
    /** 市区町村 */
    private City city;
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
    private List<SeminarOwner> owners;
    /** 埋め込みコード */
    private String embedCode;
    /** 最低開催人数定員 */
    private Integer minimumNumberHosts;
    /** 会場手配サポート */
    private boolean placeSupported;
    /** 撮影サポート */
    private boolean shootingSupported;
    /** 編集サポート */
    private boolean shootingEditSupported;
    /** 動画販売サポート */
    private boolean movieSalesSupported;
    /** 更新日時 */
    private LocalDateTime updatedAt;
    
    public Seminar() {
    }
    
    public Seminar(Long id,
            ProviderId providerId, 
            String title, 
            SeminarType seminarType, 
            LocalDateTime startingAt, 
            LocalDateTime endingAt, 
            boolean placeSupported,
            Integer prefectureId,
            Integer cityId,
            String address,
            String place) {
        this.id = id;
        this.openingStatus = OpeningStatus.DRAFT;
        this.providerId = providerId;
        this.title = title;
        this.seminarType = seminarType;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.placeSupported = placeSupported;
        if (!placeSupported) {
            this.prefecture = new Prefecture(prefectureId);
            this.city = new City(cityId);
            this.address = address;
            this.place = address;
        }
    }
    
    /**
     * コンストラクタ
     * EventonApiより取得したデータからセミナーモデルを構築します
     * @param event
     */
    public Seminar(Event event) {
        this.openingStatus = OpeningStatus.OPENING;
        this.providerId = ProviderId.EVENTON;
        this.providerSeminarId = event.getEvent_id();
        this.seminarType = SeminarType.OFFLINE;
        this.title = event.getTitle();
        this.summary = event.getSummary();
        this.contents = event.getContents();
        this.imagePath = event.getImage_path();
        if (!StringUtils.isEmpty(event.getStarted_at())) {
            this.startingAt = LocalDateTime.parse(event.getStarted_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEnded_at())) {
            this.endingAt = LocalDateTime.parse(event.getEnded_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getOpened_at())) {
            this.openingAt = LocalDateTime.parse(event.getOpened_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEntry_started_at())) {
            this.entryStartingAt = LocalDateTime.parse(event.getEntry_started_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getEntry_ended_at())) {
            this.entryEndingAt = LocalDateTime.parse(event.getEntry_ended_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        if (!StringUtils.isEmpty(event.getCancel_at())) {
            this.cancelAt = LocalDateTime.parse(event.getCancel_at(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
        this.capacity = event.getCapacity();
        this.providerSeminarUrl = event.getEvent_url();
        this.referenceUrl = event.getUrl();
        if (event.getPayment_types() != null && event.getPayment_types().size() > 0) {
            this.paymentTypes = new ArrayList<SeminarPaymentType>();
            for (String paymentTypeName: event.getPayment_types()) {
                PaymentType paymentType = PaymentType.nameOf(paymentTypeName);
                if (paymentType != null) {
                    this.paymentTypes.add(new SeminarPaymentType(paymentType));
                }
            }
        }
        if (event.getTickets() != null && event.getTickets().size() > 0) {
            this.tickets = new ArrayList<SeminarTicket>();
            for (Ticket ticket: event.getTickets()) {
                this.tickets.add(new SeminarTicket(ticket.getName(), ticket.getPrice()));
            }
        }
        
        this.cancelPolicy = event.getCancel_policy();
        if (event.getPrefecture_id() != null) {
            this.prefecture = new Prefecture(event.getPrefecture_id());
        }
        this.address = event.getAddress();
        this.place = event.getPlace();
        this.lat = event.getLat();
        this.lng = event.getLng();
        this.accepted = event.getAccepted();
        this.waiting = event.getWaiting();
        if (event.getOwners() != null && event.getOwners().size() > 0) {
            this.owners = new ArrayList<>();
            for (Owner owner: event.getOwners()) {
                this.owners.add(new SeminarOwner(owner.getId(), owner.getName(), owner.getUrl()));
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
        if (this.entryStartingAt != null) {
            return LocalDateTime.now().isAfter(this.entryStartingAt) && this.entryEndingAt.isAfter(LocalDateTime.now());
        }
        return this.entryEndingAt.isBefore(LocalDateTime.now());
    }
    
    public String remainingStatus() {
        int remain = this.capacity - this.accepted;
        if (remain < 10) {
            return "空席わずか";
        } else {
            return "空席あり";
        }
    }
    
    public boolean isOwnership(Long memberId) {
        for (SeminarOwner owner: this.owners) {
            if (owner.getMemberId().equals(memberId)) {
                return true;
            }
        }
        return false;
    }
    
    public String getHoldPlace() {
        if (ProviderId.EVENTON.equals(this.providerId)) {
            return this.address + " " + this.place;
        } else {
            if (this.prefecture != null && this.city != null) {
                return this.prefecture.getName() + this.city.getName() + this.address + " " + this.place;
            }
        }
        return null;
    }
}
