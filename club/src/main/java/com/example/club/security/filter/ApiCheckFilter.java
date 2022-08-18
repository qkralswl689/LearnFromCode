package com.example.club.security.filter;

import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApiCheckFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher;

    private String pattern;

    public ApiCheckFilter(String pattern){
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(antPathMatcher.match(pattern,request.getRequestURI())){

            boolean checkHeader = checkAuthHeader(request);

            if(checkHeader){
                filterChain.doFilter(request,response);
                return;
            }else{

                // checkHeader 가 false 반환하는 경우 JSONObject를 이용해 간단한 JSON 데이터와 403 메시지를 만들어 전송한다.
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                //Json 리턴 및 한글깨짐 수정.
                response.setContentType("application/json;charset=uft-8");
                JSONObject json = new JSONObject();

                String message = "FAIL CHECK API TOKEN";

                json.put("code" , "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);

                return;
            }

        }
        filterChain.doFilter(request,response);
    }

    // Authorization이라는 헤더값의 값을 확인하고 boolean 타입의 결과를 반환한다. 이를 이용해 doFilterInternal()에서 다음 필터로 진행할 것인지 결정한다
    private boolean checkAuthHeader(HttpServletRequest request){

        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader)){
            if(authHeader.equals("12345678")){
                checkResult = true;
            }
        }

        return checkResult;
    }
}
