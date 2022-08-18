package com.example.club.config;

import com.example.club.security.filter.ApiCheckFilter;
import com.example.club.security.filter.ApiLoginFilter;
import com.example.club.security.handler.ApiLoginFailHandler;
import com.example.club.security.handler.ClubLoginSucessHandler;
import com.example.club.security.service.ClubUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

// 어노테이션 기반의 접근제한을 설정할 수 있도록 하는 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

 // EnableGlobalMethodSecurity 어노테이션 사용으로 주석처리
//        http.authorizeRequests()
//                // 모든사용자 접근가능
//                .antMatchers("/sample/all").permitAll()
//                // USER 라는 권한이 있는 사용자만 접근가능
//               .antMatchers("/sample/member").hasRole("USER");

        // 인가/인증 문제시 로그인 화면
        http.formLogin();
        // CSRF 토큰을 발행하지 않기위한 설정
        http.csrf().disable();
        // 로그아웃 설정
        http.logout();

        // 소셜로그인 설정
        http.oauth2Login().successHandler(sucessHandler());

        // Remember me 설정 - 쿠키를 얼마나 유지할 것인지 지정 -> 초(second)단위로 설정하므로 7일간 쿠기가 유지되도록 지정했다 -> 소셜로그인시 지정 불가
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService); // 7Days

        // ApiCheckfilter/addFilterBefore 를 UsernamePasswordAuthenticationFilter 이전에 동작하도록 지정
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(),UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public ClubLoginSucessHandler sucessHandler(){
        return new ClubLoginSucessHandler(passwordEncoder());
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

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/notes/**/*");
    }

    @Bean
    // /api/login 경로로 접근할 때 동작하도록 지정 ,
    public ApiLoginFilter apiLoginFilter() throws Exception{

        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager());

        // 인증실패시 401 상태코드 반환
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }


}
