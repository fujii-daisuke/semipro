package red.semipro.domain.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import red.semipro.domain.model.Member;
import red.semipro.domain.repository.member.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService,UserDetailsService {
    
    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public boolean isExistsEmail(String email) {
        return memberRepository.isExistsEmail(email);
    }

    @Override
    public boolean isExistsUsername(String username) {
        return memberRepository.isExistsUsername(username);
    }

    @Transactional
    @Override
    public void register(Member member) {
        //DB登録
        memberRepository.insert(member);
    }

    @Override
    public boolean isExists(Member member) {
        return memberRepository.isExists(member);
    }

    @Override
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public Member findByUsername(String username) {
        return null;
    }
    
    @Transactional
    @Override
    public void subscription(Member member) {
        memberRepository.subscription(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(email)) {
            throw new UsernameNotFoundException("email is null or empry.");
          }
          Member member = memberRepository.findByEmail(email);
          if (member == null) {
            throw new UsernameNotFoundException("email not found.");
          }
          return member;
    }
    
}
