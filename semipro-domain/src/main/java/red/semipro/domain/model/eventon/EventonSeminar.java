package red.semipro.domain.model.eventon;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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

}
