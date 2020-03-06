package red.semipro.domain.repository.seminar;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarDetail;

/**
 * セミナー詳細 - repository
 */
@Repository
@Mapper
public interface SeminarDetailRepository {

    /**
     * セミナー詳細を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー詳細
     */
    SeminarDetail findOneWithDetails(Long seminarId);

}
