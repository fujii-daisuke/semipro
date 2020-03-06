package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.Seminar;

/**
 * セミナー目標 - repository
 */
@Repository
@Mapper
public interface SeminarRepository {

    /**
     * セミナー目標を取得します
     *
     * @param id セミナーID
     * @return セミナー目標
     */
    Seminar findOne(@Nonnull final Long id);

    /**
     * セミナー目標を取得します
     *
     * @param id            　セミナーID
     * @param accountId     　アカウントID
     * @param openingStatus 公開ステータス
     * @return セミナー目標
     */
    Seminar findOneBy(@Nonnull @Param("id") final Long id,
        @Nonnull @Param("accountId") final Long accountId,
        @Nullable @Param("openingStatus") final OpeningStatus openingStatus);

    /**
     * セミナー目標初期状態を登録します
     *
     * @param seminar セミナー目標
     * @return 登録件数
     */
    int initialize(@Nonnull final Seminar seminar);

    /**
     * セミナー目標を更新します
     *
     * @param seminar セミナー
     * @return 更新件数
     */
    int update(@Nonnull final Seminar seminar);

    /**
     * 公開ステータスを更新する
     *
     * @param seminarId     セミナーID
     * @param openingStatus 公開ステータス
     */
    void updateOpeningStatus(@Nonnull @Param("seminarId") final Long seminarId,
        @NotNull @Param("openingStatus") final OpeningStatus openingStatus);
}
