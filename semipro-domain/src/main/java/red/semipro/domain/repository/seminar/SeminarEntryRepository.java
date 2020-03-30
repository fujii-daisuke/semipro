package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarEntry;

/**
 * セミナーエントリー - repository
 */
@Repository
@Mapper
public interface SeminarEntryRepository {

    /**
     * セミナーエントリーを取得します
     *
     * @param seminarId       セミナーID
     * @param seminarTicketId チケットID
     * @return 件数
     */
    Integer countBySeminarIdAndTicketId(@Nonnull @Param("seminarId") final Long seminarId,
        @Nonnull @Param("seminarTicketId") final Long seminarTicketId);

    /**
     * セミナー概要を登録します
     *
     * @param seminarEntry セミナーエントリー
     * @return 登録件数
     */
    int insert(@Nonnull final SeminarEntry seminarEntry);

    boolean existsEntry(@Nonnull @Param("seminarId") final Long seminarId,
        @Nonnull @Param("accountId") final Long accountId);
}
