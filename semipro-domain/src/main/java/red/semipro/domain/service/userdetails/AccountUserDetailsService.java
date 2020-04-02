package red.semipro.domain.service.userdetails;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.account.Account;
import red.semipro.domain.repository.account.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (Objects.isNull(email)) {
            throw new UsernameNotFoundException("email is null or empry.");
        }
        Account account = accountRepository.findByEmail(email, RegisterStatus.REGULAR);
        if (account == null) {
            throw new UsernameNotFoundException("email not found.");
        }
        return new AccountUserDetails(account, null);
    }

}
