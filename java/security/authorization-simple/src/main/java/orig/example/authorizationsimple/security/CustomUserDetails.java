package orig.example.authorizationsimple.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isActive;
    Collection<GrantedAuthority> authorities;


    public CustomUserDetails(String userName, String password,
                             boolean isActive, String ... authorities) {
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.authorities = Stream.of(authorities).
                map(a->new SimpleGrantedAuthority("ROLE_" + a)).
                collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
