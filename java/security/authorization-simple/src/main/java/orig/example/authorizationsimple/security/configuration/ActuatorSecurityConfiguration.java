package orig.example.authorizationsimple.security.configuration;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off


        http.requestMatcher(EndpointRequest.toAnyEndpoint()).
                authorizeRequests().
                    requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll().
                    anyRequest().authenticated().
                and().httpBasic();
        //@formatter:off
    }
}


class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().
                httpStrictTransportSecurity(). //hsts header
                includeSubDomains(true).
                maxAgeInSeconds(31500000);
                //and().
            http.requiresChannel(). //this is only in production ! we want to redirect to https
                requestMatchers(r->r.getHeader("x-forwarded-proto")!=null).requiresSecure();
    }
}