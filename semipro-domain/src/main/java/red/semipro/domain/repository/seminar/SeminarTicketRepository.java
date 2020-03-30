package red.semipro.domain.repository.seminar;

import java.util.List;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarTicket;

/**
 * セミナーチケット - repository
 */
@Repository
@Mapper
public interface SeminarTicketRepository {

    /**
     * セミナーチケット一覧を取得する
     *
     * @param seminarId セミナーID
     * @return セミナーチケットリスト
     */
    List<SeminarTicket> findAll(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナーチケットを登録する
     *
     * @param seminarTickets セミナーチケットリスト
     * @return 登録件数
     */
    int insert(@Nonnull @Param("tickets") final List<SeminarTicket> seminarTickets);

    /**
     * セミナーチケットを削除する
     *
     * @param seminarId セミナーID
     * @return 削除件数
     */
    int delete(@Nonnull @Param("seminarId") final Long seminarId);

}
