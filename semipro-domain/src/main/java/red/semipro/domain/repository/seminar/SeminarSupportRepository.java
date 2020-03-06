package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarSupport;

/**
 * セミナーサポート - repository
 */
@Repository
@Mapper
public interface SeminarSupportRepository {

    /**
     * セミナーサポートを取得する
     *
     * @param seminarId セミナーID
     * @return セミナーサポート
     */
    SeminarSupport findOne(@Nonnull final Long seminarId);

    /**
     * セミナーサポートを登録する
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナーサポートを更新する
     *
     * @param seminarSupport セミナーサポート
     * @return 更新件数
     */
    int update(@Nonnull final SeminarSupport seminarSupport);

}
