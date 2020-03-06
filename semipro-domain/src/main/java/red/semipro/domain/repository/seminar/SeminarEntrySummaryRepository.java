package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarEntrySummary;

/**
 * セミナー申込者サマリ - repository
 */
@Repository
@Mapper
public interface SeminarEntrySummaryRepository {

    /**
     * セミナー申込者サマリを取得します
     *
     * @param seminarId 　セミナーID
     * @return セミナー申込者サマリ
     */
    SeminarEntrySummary findOne(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナー申込者サマリを登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナー申込者サマリを更新します
     *
     * @param seminarEntrySummary セミナー申込者サマリ
     * @return 更新件数
     */
    int update(@Nonnull final SeminarEntrySummary seminarEntrySummary);

}
