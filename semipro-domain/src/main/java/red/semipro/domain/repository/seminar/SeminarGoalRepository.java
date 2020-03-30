package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarGoal;

/**
 * セミナー目標 - repository
 */
@Repository
@Mapper
public interface SeminarGoalRepository {

    /**
     * セミナー目標を取得します
     *
     * @param seminarId セミナーID
     * @return セミナー目標
     */
    SeminarGoal findOneWithPlace(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナー目標初期状態を登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナー目標を更新します
     *
     * @param seminarGoal セミナー
     * @return 更新件数
     */
    int update(@Nonnull final SeminarGoal seminarGoal);

}
