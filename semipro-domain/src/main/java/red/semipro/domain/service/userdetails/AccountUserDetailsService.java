package red.semipro.domain.service.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import red.semipro.domain.model.Account;
import red.semipro.domain.service.account.AccountService;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(email)) {
            throw new UsernameNotFoundException("email is null or empry.");
          }
          Account account = accountService.findByEmail(email);
          if (account == null) {
            throw new UsernameNotFoundException("email not found.");
          }
          return new AccountUserDetails(account, null);
    }

}
