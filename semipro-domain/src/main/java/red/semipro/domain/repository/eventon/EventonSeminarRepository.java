package red.semipro.domain.repository.eventon;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.eventon.EventonSeminar;
import red.semipro.domain.model.eventon.EventonSeminarCriteria;

@Repository
@Mapper
public interface EventonSeminarRepository {

    EventonSeminar findOne(@Nonnull final Integer eventId);

    int insert(@Nonnull final EventonSeminar eventonSeminar);

    int update(@Nonnull final EventonSeminar eventonSeminar);

    List<EventonSeminar> findAllByCriteria(@Nonnull final EventonSeminarCriteria criteria);

}
