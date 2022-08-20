package com.example.club.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

public class JWTUtil {

    private String secreKey = "zerock12345678";

    // 1month
    private long expire = 60 * 24 * 30;

    // JWT 토큰 생성
    public String generateToken(String content) throws Exception {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                //.setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
                .claim("sub",content)
                .signWith(SignatureAlgorithm.HS256,secreKey.getBytes("UTF-8"))
                .compact();
    }

    // 인코딩된 문자열에서 원하는 값 추출
    // JWT 문자열 검증
    public String validateAndExtract(String tokenStr) throws Exception{

        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(secreKey.getBytes("UTF-8")).
                    parseClaimsJws(tokenStr);

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            contentValue = claims.getSubject();

        }catch (Exception e){
            contentValue = null;
        }

        return contentValue;
    }
}
