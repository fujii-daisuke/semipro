package red.semipro.domain.service.userdetails;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import red.semipro.domain.model.admin.Admin;
import red.semipro.domain.service.admin.AdminService;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (Objects.isNull(username)) {
            throw new UsernameNotFoundException("username is null or empry.");
        }
        Admin admin = adminService.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("username not found.");
        }
        return new AdminUserDetails(admin, null);
    }
}
