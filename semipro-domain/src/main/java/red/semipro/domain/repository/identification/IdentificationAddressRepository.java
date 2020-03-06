package red.semipro.domain.repository.identification;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.identification.IdentificationAddress;

/**
 * 本人確認（住所） - repository
 */
@Repository
@Mapper
public interface IdentificationAddressRepository {

    /**
     * 本人確認（住所）を登録します
     *
     * @param identificationAddress 本人確認（住所）
     * @return 登録件数
     */
    int insert(@Nonnull final IdentificationAddress identificationAddress);

    /**
     * 本人確認（住所）を更新します
     *
     * @param identificationAddress 本人確認（住所）
     * @return 更新件数
     */
    int update(@Nonnull final IdentificationAddress identificationAddress);

    /**
     * 本人確認（住所）を削除します
     *
     * @param identificationId 本人確認ID
     * @return 削除件数
     */
    int delete(@NotNull final Long identificationId);
}
