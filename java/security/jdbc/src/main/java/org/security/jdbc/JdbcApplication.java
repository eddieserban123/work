package org.security.jdbc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.h2.server.web.WebServlet;

import javax.sql.DataSource;

@SpringBootApplication
public class JdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }


    @Bean
    UserDetailsManager userDetailService(DataSource ds) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(ds);
        return jdbcUserDetailsManager;
    }

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    InitializingBean initializer(UserDetailsManager manager) {
        return () -> {
            UserDetails eddie =
                    User.withDefaultPasswordEncoder().username("eddie").password("password").roles("USER").build();
            manager.createUser(eddie);
            UserDetails titi = User.withUserDetails(eddie).username("titi").build();
            manager.createUser(titi);

        };
    }

}

