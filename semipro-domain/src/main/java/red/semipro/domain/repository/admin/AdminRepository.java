package red.semipro.domain.repository.admin;

import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import red.semipro.domain.model.admin.Admin;

/**
 * 管理者 - repository
 */
@Repository
@Mapper
public interface AdminRepository {

    /**
     * 管理者名から管理者を取得します
     *
     * @param username 管理者名
     * @return 管理者
     */
    Admin findByUsername(@Nonnull final String username);
}
