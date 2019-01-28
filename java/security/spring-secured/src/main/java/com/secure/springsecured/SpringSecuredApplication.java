package com.secure.springsecured;

import lombok.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringSecuredApplication implements CommandLineRunner {



    public static void main(String[] args) {


        SpringApplication.run(SpringSecuredApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}

@Log4j2
@Transactional
@Component
class Runner implements ApplicationRunner{

    private final MessageRepository msgRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authRepository;

    public Runner(MessageRepository msgRepository, UserRepository userRepository, AuthorityRepository authRepository) {
        this.msgRepository = msgRepository;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Authority admin = authRepository.save(new Authority("admin"));
        Authority user = authRepository.save(new Authority("user"));
        User eddie = userRepository.save(new User("eddie", "eddie", admin, user));
        Message msg = msgRepository.save(new Message("Heelo ", eddie));

        log.info("eddie " + eddie.toString());

    }
}


@Repository
interface MessageRepository extends JpaRepository<Message, Long>{}

@Repository
interface UserRepository extends JpaRepository<User, Long>{}

@Repository
interface AuthorityRepository extends JpaRepository<Authority, Long>{}


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
        this(email,password, new HashSet<>());
    }

    public User(String email, String password, Authority...authorities) {
        this(email,password, new HashSet<>(Arrays.asList(authorities)));
    }

    @ManyToMany(mappedBy = "users")
    private List<Authority> authorities;

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