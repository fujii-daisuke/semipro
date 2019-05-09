package red.semipro.domain.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import red.semipro.domain.model.Member;
import red.semipro.domain.repository.member.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
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
    public Member register(Member member) {
        memberRepository.insert(member);
        return member;
    }

    @Override
    public boolean isExists(Member member) {
        return memberRepository.isExists(member);
    }

    @Override
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
    
    @Transactional
    @Override
    public void activation(Member member) {
        memberRepository.activation(member);
    }
    
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
