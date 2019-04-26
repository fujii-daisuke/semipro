package red.semipro.business.account;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import red.semipro.domain.enums.RegisterStatus;
import red.semipro.domain.model.Member;

public class AccountUserDetails implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final Member member;
    private final Collection<? extends GrantedAuthority> authorities;
    
    public AccountUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.authorities = authorities;
    }
    
    public Member getMember() {
        return member;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
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
        return member != null && RegisterStatus.REGULAR.equals(member.getRegisterStatus());
    }

}
