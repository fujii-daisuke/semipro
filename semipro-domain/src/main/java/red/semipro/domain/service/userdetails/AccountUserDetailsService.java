package red.semipro.domain.service.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import red.semipro.domain.model.Member;
import red.semipro.domain.service.member.MemberService;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(email)) {
            throw new UsernameNotFoundException("email is null or empry.");
          }
          Member member = memberService.findByEmail(email);
          if (member == null) {
            throw new UsernameNotFoundException("email not found.");
          }
          return new AccountUserDetails(member, null);
    }

}
