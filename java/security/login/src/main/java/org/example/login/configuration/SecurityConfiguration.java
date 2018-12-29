package org.example.login.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/logout-success");

    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.jdbcAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder());
//                //.dataSource(dataSource);
//    }
}
