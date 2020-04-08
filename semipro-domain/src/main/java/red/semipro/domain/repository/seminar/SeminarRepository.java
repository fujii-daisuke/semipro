package red.semipro.domain.repository.seminar;

import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import red.semipro.domain.enums.OpeningStatus;
import red.semipro.domain.model.seminar.Seminar;
import red.semipro.domain.model.seminar.SeminarSearchCriteria;

/**
 * セミナー - repository
 */
@Repository
@Mapper
public interface SeminarRepository {

    Seminar findByIdAndOpeningStatusAndAccountId(
        @Nonnull final Long id,
        @Nonnull final OpeningStatus openingStatus,
        @Nonnull final Long accountId);

    Seminar findOneWithDetails(@Nonnull @Param("id") final Long id);

    Seminar findOneWithDetailsByIdAndAccountId(
        @Nonnull @Param("id") final Long id,
        @Nullable @Param("accountId") Long accountId);

    Seminar findOneWithDetailsByIdAndOpeningStatusAndAccountId(
        @Nonnull @Param("id") final Long id,
        @Nullable @Param("openingStatus") OpeningStatus openingStatus,
        @Nullable @Param("accountId") Long accountId);

    Seminar findOneWithDetailsByIdAndOpeningStatusList(
        @Nonnull @Param("id") final Long id,
        @Nullable @Param("openingStatusList") List<OpeningStatus> openingStatusList);

    Seminar findOneWithDetailsByIdAndOpeningStatusAndSeminarTicketId(
        @Nonnull @Param("id") final Long id,
        @Nonnull @Param("openingStatus") OpeningStatus openingStatus,
        @Nonnull @Param("seminarTicketId") Long seminarTicketId);

    /**
     * セミナー詳細を取得します
     *
     * @param id セミナーID
     * @return セミナー詳細
     */
    Seminar findOneWithDetailsForUpdate(
        @Nonnull @Param("id") final Long id,
        @Nonnull @Param("openingStatus") final OpeningStatus openingStatus);

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
    long countByCriteria(@Nonnull final @Param("criteria") SeminarSearchCriteria criteria);

    /**
     * セミナーサマリー一覧を取得する
     *
     * @param criteria 検索条件
     * @param pageable ページネーション
     * @return セミナーサマリー一覧
     */
    List<Seminar> findPageByCriteria(
        @Nonnull @Param("criteria") final SeminarSearchCriteria criteria,
        @Nonnull @Param("pageable") final Pageable pageable);

    List<Seminar> findAllEndOfRecruitment(
        @Nonnull @Param("openingStatus") final OpeningStatus openingStatus,
        @Nonnull @Param("executionDate") final LocalDate executionDate);
}
