package com.example.spring5;
















//import org.springframework.boot.SpringApplication;
//    import org.springframework.boot.autoconfigure.SpringBootApplication;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//    import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//    import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//    import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//    import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//    import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//    import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//    import org.springframework.web.bind.annotation.GetMapping;
//    import org.springframework.web.bind.annotation.RestController;
//
//@SpringBootApplication
//public class App{
//    public static void main(String[] args) {
//        SpringApplication.run(App.class, args);
//    }
//}
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig extends WebSecurityConfigurerAdapter{
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/info", "/api/**").permitAll()
//            .and()
//            .authorizeRequests().anyRequest().authenticated();
//    }
//}
//
//@Configuration
//@EnableResourceServer
//class ResourceServer extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.anonymous().disable()
//            .requestMatchers().antMatchers("/api/**").and().authorizeRequests()
//            .antMatchers("/api/**").hasRole("USER")
//            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
//    }
//}
//
//
//@Configuration
//@EnableAuthorizationServer
//class AuthorizationServer extends AuthorizationServerConfigurerAdapter{
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//            .withClient("my-trusted-client")
//            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//            .scopes("read", "write", "trust")
//            .secret("secret")
//            .accessTokenValiditySeconds(1200)
//            .refreshTokenValiditySeconds(6000);
//    }
//}
//
//@RestController
//class ApiController{
//    @GetMapping("/info")
//    public String getInfo(){
//        return "Oauth2 app";
//    }
//
//    @GetMapping("/api/version")
//    public String getApiVersion(){
//        return "v 1.1";
//    }
//}