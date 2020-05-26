package com.example.spring5;


import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

@RestController
@RequestMapping("/")
class MyController{
    @GetMapping("/public")
    public void publicGet(){
        System.out.println("publicGet");
    }
    @GetMapping("/private")
    public void privateGet(){
        System.out.println("privateGet");
    }
}

@Component
class JavaConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        AuthHeaderKeyFilter filter = new AuthHeaderKeyFilter("auth");
        /**
         * Instead of implementing you custom auth header filter you can use ready one
         *
         var filter = new RequestHeaderAuthenticationFilter();
         filter.setPrincipalRequestHeader("auth");
         */
        filter.setAuthenticationManager(auth -> {
            if (!"user".equals(auth.getPrincipal())) {
                throw new BadCredentialsException("Incorrect header auth key");
            }
            auth.setAuthenticated(true);
            return auth;
        });
        httpSecurity
            .mvcMatcher("/private")
            .addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }
}

class AuthHeaderKeyFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final String headerName;

    public AuthHeaderKeyFilter(String headerName) {
        this.headerName = headerName;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}