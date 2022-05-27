package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 모든사용자 접근가능
                .antMatchers("/sample/all").permitAll()
                // USER 라는 권한이 있는 사용자만 접근가능
               .antMatchers("/sample/member").hasRole("USER");

        // 인가/인증 문제시 로그인 화면
        http.formLogin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 사용자 계멍 : user1
        auth.inMemoryAuthentication().withUser("user1")
        // 1111 pw 인코딩 결과
        .password("$2a$10$ORfLbDWdDks2osZmIcnHu.X/aH.DxoJVI9tq.3Vw0pjGtSlr/Pzcq")
        // 권한
        .roles("USER");
    }
}
