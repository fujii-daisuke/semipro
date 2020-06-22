package red.semipro.domain.repository.seminar;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;

/**
 * セミナー - repository
 */
@Repository
@Mapper
public interface SeminarRepository {

    /**
     * セミナー詳細を取得します
     *
     * @param criteria 検索条件
     * @return セミナー
     */
    Seminar findOneWithDetails(@Nonnull @Param("criteria") final SeminarCriteria criteria);

    /**
     * セミナー詳細を取得します
     *
     * @param criteria 検索条件
     * @return セミナー
     */
    Seminar findOneWithDetailsForUpdate(@Nonnull @Param("criteria") final SeminarCriteria criteria);

    /**
     * セミナーを登録します
     *
     * @param seminar セミナー
     * @return セミナー
     */
    int insert(@Nonnull Seminar seminar);

    /**
     * 公開ステータスを更新します
     *
     * @param id            セミナーID
     * @param openingStatus 公開ステータス
     * @return 更新件数
     */
    int updateOpeningStatus(
        @Nonnull @Param("id") final Long id,
        @Nonnull @Param("openingStatus") final OpeningStatus openingStatus);

    /**
     * セミナー検索結果件数を取得
     *
     * @param criteria 検索条件
     * @return 検索結果件数
     */
    long countByCriteria(@Nonnull final @Param("criteria") SearchSeminarCriteria criteria);

    /**
     * セミナーサマリー一覧を取得する
     *
     * @param criteria 検索条件
     * @param pageable ページネーション
     * @return セミナーサマリー一覧
     */
    List<Seminar> findPageBySearchCriteria(
        @Nonnull @Param("criteria") final SearchSeminarCriteria criteria,
        @Nonnull @Param("pageable") final Pageable pageable);

    List<Seminar> findAllBySearchCriteria(
        @Nonnull @Param("criteria") final SearchSeminarCriteria criteria);

    /**
     * セミナーを検索します
     *
     * @param criteria 検索条件
     * @return セミナーリスト
     */
    List<Seminar> findAllByCriteria(@Nonnull @Param("criteria") final SeminarCriteria criteria);
}
