package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {

    //user creationa and login password using java in memory service
    // private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    // @Bean
    // public UserDetailsService userDetailsService(){

    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //     .username("admin")
    //     .password("admin")
    //     .roles("ADMIN","USER")
    //     .build();

    //     UserDetails user2 =User 
    //     .withDefaultPasswordEncoder()
    //     .username("user")
    //     .password("password")
    //     .build();


    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager; 
    // }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service object
        daoAuthenticationProvider.setUserDetailsService(null);
        //password encoader obj
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
