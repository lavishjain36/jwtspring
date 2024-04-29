package com.guvi.springsecurityapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

//    bean UserDetailService to create user details-admin,user

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        //create admin->lavish and lavish123->ROLE_ADMIN
//        UserDetails admin= User.withUsername("lavish")
//                .password(encoder.encode("lavish123"))
//                .roles("ADMIN")
//                .build();
//
//
//
//        //create user->ramya and ramya123->ROLE_USER
//        UserDetails user= User.withUsername("ramya")
//                .password(encoder.encode("ramya123"))
//                .roles("USER")
//                .build();
//
//
//
//
//        //Inmemory User details Manager
//        return  new InMemoryUserDetailsManager(admin,user);
//
//    }

    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserInfoUserDetailsService();
    }

    //Bean SecurityFilterChain to configure http security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return  http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome","/products/new").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/products/**")
                .authenticated().and().formLogin()
                .and().build();
    }



    //Bean of AuthentiCationProvider
    //configured->UserDetailsService and PasswordEncoder.

    @Bean
    public AuthenticationProvider authenticationProvider(){
        //instance of DaoAuthenticationProvider
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();

        //set the user detail service
        authenticationProvider.setUserDetailsService(userDetailsService());

        //password encoder
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return  config.getAuthenticationManager();
    }
}
