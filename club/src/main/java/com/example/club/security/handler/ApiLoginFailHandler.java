package com.example.club.security.handler;

import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApiLoginFailHandler implements AuthenticationFailureHandler {

    // 인증 실패시
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // json 리턴
        response.setContentType("applicaiont/json;charset=utf-8");
        JSONObject json = new JSONObject();

        String message = exception.getMessage();
        json.put("code","401");
        json.put("message",message);

        PrintWriter out = response.getWriter();
        out.print(json);
    }




}
