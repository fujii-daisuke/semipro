package red.semipro.domain.repository.eventon;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.eventon.EventonSeminarTicket;

@Repository
@Mapper
public interface EventonSeminarTicketRepository {

    List<EventonSeminarTicket> findAllByEventId(@Nonnull final Integer eventId);

    int insert(@Nonnull @Param("tickets") final List<EventonSeminarTicket> tickets);

    int delete(@Nonnull final Integer eventId);
}
