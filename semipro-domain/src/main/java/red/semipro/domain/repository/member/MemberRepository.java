package red.semipro.domain.repository.member;

import org.apache.ibatis.annotations.Mapper;

import red.semipro.domain.model.Member;

@Mapper
public interface MemberRepository {
    
    Member findOne(Long id);
    Member findByUsername(String username);
    Member findByEmail(String email);
    boolean isExistsEmail(String email);
    boolean isExistsUsername(String username);
    int insert(Member member);
    boolean isExists(Member member);
    int subscription(Member member);
}
