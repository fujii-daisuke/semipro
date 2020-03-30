package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarPlace;

/**
 * セミナー開催地 - repository
 */
@Repository
@Mapper
public interface SeminarPlaceRepository {

    /**
     * セミナー開催地を初期登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナー開催地を更新します
     *
     * @param seminarPlace セミナー開催地
     * @return 更新件数
     */
    int update(@Nonnull final SeminarPlace seminarPlace);
}
