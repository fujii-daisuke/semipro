package red.semipro.domain.service.account;

import red.semipro.domain.model.Account;

public interface AccountService {

    public Account findOne(Long id);
    public Account findByEmail(String email);
    public boolean isExistsEmail(String email);
    public boolean isExistsUsername(String username);
    public Account register(Account account);
    public boolean isExists(Account account);
    public void activation(Account account);

}
