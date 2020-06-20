package gc.garcol.nalsolution.authentication;

import gc.garcol.nalsolution.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author thai-van
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDetail implements UserDetails {

    private long id;

    private String email;

    private String secretKey;

    private boolean isEnable;

    private Collection<? extends GrantedAuthority> authorities;

    public long getId() {
        return id;
    }

    public static SimpleUserDetail of(Account account) {
        return SimpleUserDetail.builder()
                .id(account.getId())
                .email(account.getEmail())
                .isEnable(true)
                .secretKey("garcol")
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return secretKey;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEnable;
    }
}
