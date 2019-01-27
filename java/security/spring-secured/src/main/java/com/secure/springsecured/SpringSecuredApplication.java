package com.secure.springsecured;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class SpringSecuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecuredApplication.class, args);
    }

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

}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    @GeneratedValue
    @Id
    private Long id;

    private String email;

    private String password;


    @ManyToMany(mappedBy = "users")
    private List<Authority> authorities;

}

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
class Authority {
    @GeneratedValue
    @Id
    private Long id;

    private String authority;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "authority_user",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;


}