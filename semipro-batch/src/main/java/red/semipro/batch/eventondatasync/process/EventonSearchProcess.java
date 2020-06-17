package red.semipro.batch.eventondatasync.process;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import red.semipro.domain.eventon.model.Event;
import red.semipro.domain.eventon.repository.EventonCriteria;
import red.semipro.domain.eventon.repository.EventonRepository;

@Component
@RequiredArgsConstructor
public class EventonSearchProcess {

    private final EventonRepository eventonRepository;

    public List<Event> execute() {
        return eventonRepository.search(EventonCriteria.builder().build());
    }
}
