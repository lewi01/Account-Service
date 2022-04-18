package com.lewisCode.accountservice.configuration;

import com.lewisCode.accountservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
                .and()
                .csrf().disable() // for Postman
                .authorizeRequests() // manage access
                .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("ACCOUNTANT","USER")
                .antMatchers(HttpMethod.POST, "/api/acct/payments").hasRole("ACCOUNTANT")
                .antMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole("ACCOUNTANT")
                .antMatchers(HttpMethod.GET, "/api/admin/user/").hasRole("ADMINISTRATION")
                .antMatchers(HttpMethod.PUT, "/api/admin/user/roles").hasRole("ADMINISTRATION")
                .antMatchers(HttpMethod.DELETE, "/api/admin/user/*").hasRole("ADMINISTRATION")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}