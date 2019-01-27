package orig.example.authorizationsimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import orig.example.authorizationsimple.security.CustomUserDetailService;
import orig.example.authorizationsimple.security.CustomUserDetails;

import java.util.Arrays;

@SpringBootApplication
public class AuthorizationSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationSimpleApplication.class, args);
    }


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    UserDetailsService getUserDetailService() {
        return new CustomUserDetailService(Arrays.asList(
                new CustomUserDetails("eddie", getPasswordEncoder().encode("eddie"),
                        true, "ADMIN", "USER"),
                new CustomUserDetails("titi", getPasswordEncoder().encode("titi"),
                        true, "USER")
        ));
    }
}

