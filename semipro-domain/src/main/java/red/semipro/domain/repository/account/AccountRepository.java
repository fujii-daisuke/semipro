package red.semipro.domain.repository.account;

import org.apache.ibatis.annotations.Mapper;

import red.semipro.domain.model.Account;

@Mapper
public interface AccountRepository {
    
    Account findOne(Long id);
    Account findByUsername(String username);
    Account findByEmail(String email);
    boolean isExistsEmail(String email);
    boolean isExistsUsername(String username);
    int insert(Account account);
    boolean isExists(Account account);
    int activation(Account account);
}
