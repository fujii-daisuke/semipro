package red.semipro.domain.repository.identification;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.identification.Identification;

/**
 * 本人確認 - repository
 */
@Repository
@Mapper
public interface IdentificationRepository {

    Identification findOneWithDetails(@NotNull final Long seminarId);

    /**
     * 本人確認を初期登録します
     *
     * @param seminarId セミナーID
     * @return 登録件数
     */
    int initialize(@Nonnull final Long seminarId);

    /**
     * 本人確認を更新します
     *
     * @param identification 本人確認
     * @return 更新件数
     */
    int update(@Nonnull final Identification identification);

}
