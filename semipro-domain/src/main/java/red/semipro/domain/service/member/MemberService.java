package red.semipro.domain.service.member;

import red.semipro.domain.model.Member;

public interface MemberService {

    public Member findOne(Long id);
    public Member findByEmail(String email);
    public boolean isExistsEmail(String email);
    public boolean isExistsUsername(String username);
    public void register(Member member);
    public boolean isExists(Member member);
    public void activation(Member member);

}
