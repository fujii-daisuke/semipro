package red.semipro.domain.model.eventon;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

    Integer event_id;
    String title;
    String summary;
    String contents;
    String image_path;
    String started_at;
    String ended_at;
    String opened_at;
    String entry_started_at;
    String entry_ended_at;
    String cancel_at;
    Integer capacity;
    String event_url;
    String url;
    List<String> payment_types;
    List<Ticket> tickets;
    String cancel_policy;
    Integer prefecture_id;
    String address;
    String place;
    Double lat;
    Double lng;
    Integer accepted;
    Integer waiting;
    List<String> categories;
    List<Owner> owners;
    String embed_code;
    String updated_at;
}
