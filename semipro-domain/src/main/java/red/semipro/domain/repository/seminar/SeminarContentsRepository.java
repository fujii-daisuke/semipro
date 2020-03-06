package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarContents;

/**
 * セミナーコンテンツ - repository
 */
@Repository
@Mapper
public interface SeminarContentsRepository {

    /**
     * セミナーコンテンツを取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナーコンテンツ
     */
    SeminarContents findOne(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナーコンテンツを登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナーコンテンツを更新します
     *
     * @param seminarContents セミナーコンテンツ
     * @return 更新件数
     */
    int update(@Nonnull final SeminarContents seminarContents);

}
