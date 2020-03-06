package red.semipro.domain.repository.identification;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.identification.IdentificationCompany;

/**
 * 本人確認（法人） - repository
 */
@Repository
@Mapper
public interface IdentificationCompanyRepository {

    /**
     * 本人確認（法人）を登録します
     *
     * @param identificationCompany 本人確認（法人）
     * @return 登録件数
     */
    int insert(@Nonnull final IdentificationCompany identificationCompany);

    /**
     * 本人確認（個人）を更新します
     *
     * @param identificationCompany 本人確認（法人）
     * @return 更新件数
     */
    int update(@Nonnull final IdentificationCompany identificationCompany);

    /**
     * 本人確認（法人）を削除します
     *
     * @param identificationId 本人確認ID
     * @return 削除件数
     */
    int delete(@NotNull final Long identificationId);

}
