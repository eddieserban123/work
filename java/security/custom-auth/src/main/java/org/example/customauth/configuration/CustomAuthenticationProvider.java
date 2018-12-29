package org.example.customauth.configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

    private boolean isValid(String username, String password) {
        return username.equals("eddie") && password.equals("123");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pass = authentication.getCredentials().toString();
        if(isValid(username, pass)) {
            return new UsernamePasswordAuthenticationToken(username, pass,
                    authorities);
        }
        throw new BadCredentialsException("invalid " + username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
