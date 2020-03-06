package red.semipro.domain.repository.identification;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.identification.Identification;

/**
 * 本人確認 - repository
 */
@Repository
@Mapper
public interface IdentificationRepository {

    /**
     * 本人確認を登録します
     *
     * @param identification 本人確認
     * @return 登録件数
     */
    int insert(@Nonnull final Identification identification);

    /**
     * 本人確認を更新します
     *
     * @param identification 本人確認
     * @return 更新件数
     */
    int update(@Nonnull final Identification identification);

}
