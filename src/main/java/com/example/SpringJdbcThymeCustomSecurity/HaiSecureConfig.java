package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class HaiSecureConfig {
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager manager=new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        return manager;
//    }

    @Autowired
    UserService userService;

    AuthenticationManager authenticationManager;

    @Autowired
    HaiLoginSuccessHandler successHandler;

    @Autowired
    HaiLoginFailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        //httpSecurity.formLogin().loginPage("/web/login").defaultSuccessUrl("/web/dash").failureUrl("/web/login").permitAll();
        httpSecurity.logout().permitAll();
        httpSecurity.formLogin().loginPage("/web/login").usernameParameter("username").failureHandler(failureHandler).successHandler(successHandler).permitAll();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/user/signup").permitAll();
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        AuthenticationManagerBuilder builder=httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService);
        authenticationManager=builder.build();
        httpSecurity.authenticationManager(authenticationManager);

        return httpSecurity.build();
    }
}
