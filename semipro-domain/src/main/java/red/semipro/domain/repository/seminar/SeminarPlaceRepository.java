package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarPlace;

/**
 * セミナー開催地 - repository
 */
@Repository
@Mapper
public interface SeminarPlaceRepository {

    /**
     * セミナー開催地を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー開催地
     */
    SeminarPlace findOne(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナー開催地を登録します
     *
     * @param seminarPlace セミナー開催地
     * @return 登録件数
     */
    int insert(@Nonnull final SeminarPlace seminarPlace);

    /**
     * セミナー開催地を更新します
     *
     * @param seminarPlace セミナー開催地
     * @return 更新件数
     */
    int update(@Nonnull final SeminarPlace seminarPlace);
}
