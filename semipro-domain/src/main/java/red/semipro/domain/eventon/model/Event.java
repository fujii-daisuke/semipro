package red.semipro.domain.eventon.model;

import java.util.List;
import lombok.Data;

@Data
public class Event {

    private Integer event_id;
    private String title;
    private String summary;
    private String contents;
    private String image_path;
    private String started_at;
    private String ended_at;
    private String opened_at;
    private String entry_started_at;
    private String entry_ended_at;
    private String cancel_at;
    private Integer capacity;
    private String event_url;
    private String url;
    private List<String> payment_types;
    private List<Ticket> tickets;
    private String cancel_policy;
    private Integer prefecture_id;
    private String address;
    private String place;
    private Double lat;
    private Double lng;
    private Integer accepted;
    private Integer waiting;
    private List<String> categories;
    private List<Owner> owners;
    private String embed_code;
    private String updated_at;
}
