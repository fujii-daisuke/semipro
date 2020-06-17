package red.semipro.domain.repository.eventon;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.eventon.EventonSeminarOwner;

@Repository
@Mapper
public interface EventonSeminarOwnerRepository {

    public int insertOrUpdate(@Nonnull final EventonSeminarOwner owner);
}
