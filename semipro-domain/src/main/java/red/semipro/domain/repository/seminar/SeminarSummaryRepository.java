package red.semipro.domain.repository.seminar;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;
import red.semipro.domain.model.seminar.SeminarSummary;

/**
 * セミナーサマリー - repository
 */
@Repository
@Mapper
public interface SeminarSummaryRepository {

    /**
     * セミナー検索結果件数を取得
     *
     * @param criteria 検索条件
     * @return 検索結果件数
     */
    long countByCriteria(@Nonnull final @Param("criteria") SeminarSearchCriteria criteria);

    /**
     * セミナーサマリー一覧を取得する
     *
     * @param criteria 検索条件
     * @param pageable ページネーション
     * @return セミナーサマリー一覧
     */
    List<SeminarSummary> findPageByCriteria(
        @Nonnull @Param("criteria") final SeminarSearchCriteria criteria,
        @Nonnull @Param("pageable") final Pageable pageable);

}
