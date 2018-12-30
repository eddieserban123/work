package org.example.customauth.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CustomUserDetailService  implements UserDetailsService {

    Map<String, UserDetails> userDetails = new ConcurrentHashMap<>();

    public CustomUserDetailService(Collection<UserDetails> seedUSers) {
       seedUSers.forEach(su-> this.userDetails.put(su.getUsername(),su));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userDetails.containsKey(username)) {
            return userDetails.get(username);
        }
        throw new UsernameNotFoundException("");

    }
}
