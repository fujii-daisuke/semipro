package red.semipro.domain.eventon.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    Integer count;
    Integer start;
    Integer limit;
    List<Event> events;
}
