package red.semipro.share.userdetails;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.service.account.AccountService;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (Objects.isNull(email)) {
            throw new UsernameNotFoundException("email is null or empry.");
        }
        Account account = accountService.findByEmail(email, RegisterStatus.REGULAR);
        if (account == null) {
            throw new UsernameNotFoundException("email not found.");
        }
        return new AccountUserDetails(account, null);
    }

}
