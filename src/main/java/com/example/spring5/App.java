package com.example.spring5;
/**
 *
 * is it bad if we `http.csrf().disable()` or remove this line (why do we need such a line in the first place => if we don't use it post/put don't work without passing this token)
 * what does it mean `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` (why do we need it)
 * @Data on classes that extends each other (cause fields from base class not in toString)
 * validate cors issue, compare simple vs non-simple requests, check how different headers like `Access-Control-Request-Method/Access-Control-Request-Headers` affect response
 * check how cors on spring , why return cors not found instead of desired cors.
 * withCredentials doesn't allow * as allowed_origin, test in firefox => it shows pre-flight OPTIONS request
 * https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html
 * if SqsMessageDeletionPolicy.NO_REDRIVE remove message on success (without manual message deletion)
 * spring & hibernate sharding (https://github.com/apache/shardingsphere)
 */

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class App{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

@RestController
@RequestMapping("/")
class MyController{
    @GetMapping("/private")
    public String privateGet(){
        return "get";
    }
    @PostMapping("/private")
    public String privatePut(){
        return "put";
    }
}

@Configuration
@EnableWebSecurity
class JavaConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var filter = new RequestHeaderAuthenticationFilter();
        filter.setPrincipalRequestHeader("auth");
        filter.setAuthenticationManager(auth -> {
            if ("user".equals(auth.getPrincipal())) {
                return new UsernamePasswordAuthenticationToken("admin", null, List.of(()->"ROLE_USER"));
            }
            throw new BadCredentialsException("Incorrect header auth key");
        });

        http
            .csrf().disable()
            .mvcMatcher("/private/**")
            .addFilterAfter(filter, BasicAuthenticationFilter.class)
            .authorizeRequests().anyRequest().authenticated()
        .and().exceptionHandling().accessDeniedHandler((a,b,c)->{
            System.out.println(c);
        })
        ;
    }
}

@Configuration
class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://mysite.com")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}


