package red.semipro.domain.repository.identification;

import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.identification.IdentificationDetail;

/**
 * 本人確認（詳細） - repository
 */
@Repository
@Mapper
public interface IdentificationDetailRepository {

    IdentificationDetail findOneBySeminarId(@NotNull final Long seminarId);
}
