package red.semipro.domain.service.userdetails;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.account.Account;

public class AccountUserDetails implements UserDetails {

    private static final long serialVersionUID = -2310000986072616321L;
    private final Account account;
    private final Collection<? extends GrantedAuthority> authorities;

    public AccountUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        this.account = account;
        this.authorities = authorities;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account != null && RegisterStatus.REGULAR.equals(account.getRegisterStatus());
    }
}
