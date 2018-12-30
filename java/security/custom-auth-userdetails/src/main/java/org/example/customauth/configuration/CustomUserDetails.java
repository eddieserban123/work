package org.example.customauth.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomUserDetails implements UserDetails {

    String username;
    String password;
    boolean isActive;
    Set<GrantedAuthority> authorityList;


    public CustomUserDetails(String username, String password, boolean isActive, String...authority) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        authorityList =
                Stream.of(authority).
                        map(SimpleGrantedAuthority::new).
                        collect(Collectors.toSet());


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
