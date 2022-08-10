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
        // CSRF 토큰을 발행하지 않기위한 설정
        http.csrf().disable();
        // 로그아웃 설정
        http.logout();

        // 소셜로그인 설정
        http.oauth2Login();
    }

    // ClubUserDetailsService가 빈(Bean)으로 등록되면서 자동으로 스프링 시큐리티에서 UserDetailsService로 인식하기 때문에 아래 코드는 사용하지 않음
  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 사용자 계멍 : user1
        auth.inMemoryAuthentication().withUser("user1")
        // 1111 pw 인코딩 결과
        .password("$2a$10$ORfLbDWdDks2osZmIcnHu.X/aH.DxoJVI9tq.3Vw0pjGtSlr/Pzcq")
        // 권한
        .roles("USER");
    }*/
}
