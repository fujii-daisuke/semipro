package red.semipro.domain.repository.eventon;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.eventon.EventonSeminarOwner;

@Repository
@Mapper
public interface EventonSeminarOwnerRepository {

    int insert(@Nonnull @Param("owners") final List<EventonSeminarOwner> owners);

    int delete(@Nonnull final Integer eventId);

}
