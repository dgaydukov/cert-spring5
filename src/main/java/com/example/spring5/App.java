package com.example.spring5;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().anyRequest().authenticated()
            .and().formLogin()
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // when we are using AuthenticationProvider it overrides UserDetailsService
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication auth) throws AuthenticationException {
                // here we have access to password
                System.out.println("authenticate => " + auth.getPrincipal() + "/" + auth.getCredentials());
                return new UsernamePasswordAuthenticationToken("admin", "admin", List.of(()->"ROLE_USER"));
            }

            @Override
            public boolean supports(Class<?> cls) {
                return true;
            }
        });
    }
}

@RestController
class ApiController{
    @GetMapping("/api/info")
    public String getApiInfo(){
        return "Api v2.0";
    }
}