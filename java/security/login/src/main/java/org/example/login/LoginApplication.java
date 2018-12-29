package org.example.login;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    @Bean
    UserDetailsManager userDetailService() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    InitializingBean initializer(UserDetailsManager manager) {
        return () -> {
            UserDetails eddie =
                    User.withDefaultPasswordEncoder().username("eddie").password("eddie").roles("USER").build();
            manager.createUser(eddie);
            UserDetails titi = User.withUserDetails(eddie).username("titi").build();
            manager.createUser(titi);

        };
    }

}

