package com.secure.springsecured;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.security.RolesAllowed;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
@SpringBootApplication
public class SpringSecuredApplication implements CommandLineRunner {


    public static void main(String[] args) {


        SpringApplication.run(SpringSecuredApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}


class MyUserDetails implements UserDetails {

    private User user;
    private Set<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.user = user;
        authorities = user.getAuthorities().stream().
                map(a -> new SimpleGrantedAuthority("ROLE_" + a.getAuthority())).
                collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return true;
    }
}

@Component
class MyUserDetailService implements UserDetailsService {


    private UserRepository userRepo;

    public MyUserDetailService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails us = new MyUserDetails(userRepo.findUserByEmail(s));
        if (us == null) {
            throw new InvalidParameterException(s);
        }
        return us;
    }
}


@Log4j2
@Transactional
@Component
class Runner implements ApplicationRunner {

    private final MessageRepository msgRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authRepository;
    private final UserDetailsService userDetailService;

    public Runner(MessageRepository msgRepository, UserRepository userRepository,
                  AuthorityRepository authRepository, MyUserDetailService userDetailsService) {
        this.msgRepository = msgRepository;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.userDetailService = userDetailsService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Authority admin = authRepository.save(new Authority("ADMIN"));
        Authority user = authRepository.save(new Authority("USER"));
        User eddie = userRepository.save(new User("eddie", "eddie", admin, user));
        User cocos = userRepository.save(new User("titi", "titi", user));

        Message msg1 = msgRepository.save(new Message("Heelo ", eddie));
        Message msg2 = msgRepository.save(new Message("hey ", cocos));


//        log.info("eddie " + eddie.toString());

        authenticate(eddie.getEmail());
        log.info("**** " + msgRepository.findByIdMessageAllowed(msg1.getId()));


        authenticate(cocos.getEmail());
        try {
            log.info("**** " + msgRepository.findByIdMessageAllowed(msg1.getId()));

        } catch (Throwable e) {
            log.error("couldn't obtain result foe cocos ");
        }
    }


    private void authenticate(String username) {
        UserDetails us = userDetailService.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(us.getUsername(),
                us.getPassword(), us.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}


@Repository
interface MessageRepository extends JpaRepository<Message, Long> {

    String QUERY = "select m from Message m where m.id = ?1";

    @Query(QUERY)
    @RolesAllowed("ROLE_ADMIN")
    Message findByIdMessageAllowed(Long Id);
}

@Repository
interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}

@Repository
interface AuthorityRepository extends JpaRepository<Authority, Long> {
}


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
class Message {
    @GeneratedValue
    @Id
    private Long id;

    private String text;

    @OneToOne
    private User to;

    public Message(String text, User to) {
        this.text = text;
        this.to = to;
    }
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "authorities")
@Data
class User {
    @GeneratedValue
    @Id
    private Long id;
    private String email;
    private String password;

    public User(String email, String password, Set<Authority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities.addAll(authorities);
    }


    public User(String email, String password) {
        this(email, password, new HashSet<>());
    }

    public User(String email, String password, Authority... authorities) {
        this(email, password, new HashSet<>(Arrays.asList(authorities)));
    }

    @ManyToMany(mappedBy = "users")
    private List<Authority> authorities = new ArrayList<>();

}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "users")
@Data
class Authority {
    @GeneratedValue
    @Id
    private Long id;

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(String authority, Set<User> users) {
        this.authority = authority;
        this.users.addAll(users);

    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "authority_user",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();


}