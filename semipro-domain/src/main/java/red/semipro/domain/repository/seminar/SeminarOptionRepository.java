package red.semipro.domain.repository.seminar;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.seminar.SeminarOption;

/**
 * セミナーオプション - repository
 */
@Repository
@Mapper
public interface SeminarOptionRepository {

    /**
     * セミナーオプションを取得する
     *
     * @param seminarId セミナーID
     * @return セミナーサポート
     */
    SeminarOption findOne(@Nonnull @Param("seminarId") final Long seminarId);

    /**
     * セミナーオプションを登録する
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * セミナーオプションを更新する
     *
     * @param seminarOption セミナーオプション
     * @return 更新件数
     */
    int update(@Nonnull final SeminarOption seminarOption);

}
