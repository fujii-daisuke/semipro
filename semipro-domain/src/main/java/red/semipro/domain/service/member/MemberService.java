package red.semipro.domain.service.member;

import red.semipro.domain.model.Member;

public interface MemberService {

    Member findOne(Long id);
    Member findByUsername(String username);
    boolean isExistsEmail(String email);
    boolean isExistsUsername(String username);
    void register(Member member);
    boolean isExists(Member member);
    void subscription(Member member);

}
