package red.semipro.domain.eventon.repository;

import java.util.List;
import javax.annotation.Nonnull;
import red.semipro.domain.eventon.model.Event;

public interface EventonRepository {

    List<Event> search(@Nonnull final EventonCriteria criteria);
}
