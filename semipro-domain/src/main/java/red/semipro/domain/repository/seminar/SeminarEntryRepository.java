package red.semipro.domain.repository.seminar;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarEntry;
import red.semipro.domain.model.seminar.SeminarEntryCount;

/**
 * セミナーエントリー - repository
 */
@Repository
@Mapper
public interface SeminarEntryRepository {

    List<SeminarEntry> findAllWithTicketsBySeminarId(
        @Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナーエントリーを取得します
     *
     * @param seminarId       セミナーID
     * @param seminarTicketId チケットID
     * @return 件数
     */
    int countBySeminarIdAndTicketId(
        @Nonnull @Param("seminarId") final Long seminarId,
        @Nonnull @Param("seminarTicketId") final Long seminarTicketId);

    /**
     *
     * @param seminarId
     * @return
     */
    List<SeminarEntryCount> countBySeminarIdGroupBySeminarTicketId(
        @Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナー概要を登録します
     *
     * @param seminarEntry セミナーエントリー
     * @return 登録件数
     */
    int insert(@Nonnull final SeminarEntry seminarEntry);

    /**
     *
     * @param seminarId
     * @param accountId
     * @return
     */
    boolean existsEntry(
        @Nonnull @Param("seminarId") final Long seminarId,
        @Nonnull @Param("accountId") final Long accountId);

    /**
     * エントリー済みのチケットID一覧を取得します
     * @param seminarId セミナーID
     * @param accountId アカウントID
     * @return チケットIDリスト
     */
    List<Long> findAllBySeminarIdAndAccountId(
        @Nonnull @Param("seminarId") final Long seminarId,
        @Nonnull @Param("accountId") final Long accountId);

    int updateStripeTransferId(@Nonnull @Param("id") Long id,
        @Nonnull @Param("stripeTransferId") String stripeTransferId);

    int updateStripeRefundId(@Nonnull @Param("id") Long id,
        @Nonnull @Param("stripeRefundId") String stripeRefundId);

}
