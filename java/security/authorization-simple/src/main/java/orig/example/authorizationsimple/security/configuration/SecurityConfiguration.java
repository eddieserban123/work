package orig.example.authorizationsimple.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();

        http.authorizeRequests().
                mvcMatchers("/root").hasAnyAuthority("ROLE_ADMIN").
                mvcMatchers(HttpMethod.GET, "/a").access("hasRole('ROLE_ADMIN')").
                mvcMatchers(HttpMethod.POST,"/b").
                access("@authz.check(request,principal)").
                mvcMatchers("/users/{name}").access("#name==principal?.username").
                anyRequest().permitAll();
    }
}
