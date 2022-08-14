package com.example.club.security.handler;

import com.example.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClubLoginSucessHandler implements AuthenticationSuccessHandler
{
    //소셜 로그인 대상 URL을 다르게 지정하는 용도로 사용
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public ClubLoginSucessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

        if(fromSocial && passwordResult){
            redirectStrategy.sendRedirect(request,response,"/member/modify?from=social");
        }
    }
}
