package red.semipro.domain.service.admin;

import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import red.semipro.domain.model.admin.Admin;
import red.semipro.domain.repository.admin.AdminRepository;

/**
 * 管理者 - service
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * 管理者名から管理者を取得します
     *
     * @param username       管理者名
     * @return boolean true 存在する / false 存在しない
     */
    public Admin findByUsername(@Nonnull final String username) {
        return adminRepository.findByUsername(username);
    }

}
