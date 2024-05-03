package com.example.myBoard.config;

import com.example.myBoard.service.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthFailureHandler();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
//                        .requestMatchers("/**").permitAll())
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
//                        .failureForwardUrl("/user/login")
//                        .usernameParameter("email")
                        .defaultSuccessUrl("/articles/paging", true))

                .logout(out->out
                        .logoutSuccessUrl("/")
                        .logoutUrl("/logout"))
<<<<<<< HEAD
=======

                .oauth2Login(oAuth->oAuth
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo->
                                userInfo.userService(principalOauth2UserService)))

>>>>>>> f4545c3 (#.OAuth2)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
