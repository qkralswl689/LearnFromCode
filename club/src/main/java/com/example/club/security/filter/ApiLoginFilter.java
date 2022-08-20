package com.example.club.security.filter;

import com.example.club.security.dto.ClubAuthMemberDTO;
import com.example.club.security.util.JWTUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;

    public ApiLoginFilter(String defaultFilterProcessesUrl,JWTUtil jwtUtil){
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

       String email = request.getParameter("email");

       String pw = request.getParameter("pw");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,pw);



       if(email == null){
           throw new BadCredentialsException("email cannot be null");
       }
        return getAuthenticationManager().authenticate(authToken);
    }

    // 인증 성공시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String email = ((ClubAuthMemberDTO) authResult.getPrincipal()).getUsername();
        String token = null;
        try {

            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
