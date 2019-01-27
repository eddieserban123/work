package orig.example.authorizationsimple.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CustomUserDetailService implements UserDetailsService {

    private Map<String, UserDetails> users = new ConcurrentHashMap<>();


    public CustomUserDetailService(List<UserDetails> us) {

        us.forEach(u-> users.put(u.getUsername(), u));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(users.containsKey(username)) {
            return users.get(username);
        };
        throw new BadCredentialsException("invalid " + username + " !");
    }
}
