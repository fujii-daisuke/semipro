package red.semipro.domain.model.eventon;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.semipro.domain.model.address.Prefecture;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventonSeminar implements Serializable {

    private static final long serialVersionUID = 7696907524433686663L;
    private Integer eventId;
    private String title;
    private String summary;
    private String contents;
    private String imagePath;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime openedAt;
    private LocalDateTime entryStartedAt;
    private LocalDateTime entryEndedAt;
    private LocalDateTime cancelAt;
    private Integer capacity;
    private String eventUrl;
    private String url;
    private List<String> paymentTypes;
    private List<EventonSeminarTicket> tickets;
    private String cancelPolicy;
    private Prefecture prefecture;
    private String address;
    private String place;
    private Double lat;
    private Double lng;
    private Integer accepted;
    private Integer waiting;
    private List<String> categories;
    private List<EventonSeminarOwner> owners;
    private String embedCode;
    private LocalDateTime updatedAt;

    /**
     * 募集終了日までの残日数を取得します
     *
     * @return 残日数
     */
    public Long getEntryRemainingDays() {
        if (Objects.isNull(entryEndedAt)) {
            return 0L;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), entryEndedAt);
    }

}
