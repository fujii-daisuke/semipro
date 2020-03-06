package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarOverview;

/**
 * セミナー概要 - repository
 */
@Repository
@Mapper
public interface SeminarOverviewRepository {

    /**
     * セミナー概要を取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナー概要
     */
    SeminarOverview findOne(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナー概要を登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナー概要を更新します
     *
     * @param seminarOverview セミナー概要
     * @return 更新件数
     */
    int update(@Nonnull final SeminarOverview seminarOverview);

    /**
     * メイン画像の拡張子を更新します
     *
     * @param seminarId          セミナーID
     * @param mainImageExtension メイン画像拡張子
     * @return 更新件数
     */
    int updateMainImageExtension(@NotNull final Long seminarId,
        @Nullable final String mainImageExtension);

}
