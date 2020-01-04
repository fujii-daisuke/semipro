package red.semipro.domain.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import red.semipro.domain.model.Account;
import red.semipro.domain.repository.account.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public boolean isExistsEmail(String email) {
        return accountRepository.isExistsEmail(email);
    }

    @Override
    public boolean isExistsUsername(String username) {
        return accountRepository.isExistsUsername(username);
    }

    @Transactional
    @Override
    public Account register(Account account) {
        accountRepository.insert(account);
        return account;
    }

    @Override
    public boolean isExists(Account account) {
        return accountRepository.isExists(account);
    }

    @Override
    public Account findOne(Long id) {
        return accountRepository.findOne(id);
    }
    
    @Transactional
    @Override
    public void activation(Account account) {
        accountRepository.activation(account);
    }
    
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
