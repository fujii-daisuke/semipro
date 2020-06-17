package red.semipro.domain.repository.eventon;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.eventon.EventonSeminarTicket;

@Repository
@Mapper
public interface EventonSeminarTicketRepository {

    int insert(@Nonnull final List<EventonSeminarTicket> tickets);

    int delete(@Nonnull final Integer eventId);
}