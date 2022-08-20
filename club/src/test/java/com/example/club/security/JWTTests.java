package com.example.club.security;

import com.example.club.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){

        jwtUtil = new JWTUtil();

    }

    // 암호화 검증 테스트
    @Test
    public void testEncode() throws Exception{

        String email = "user10@com.example";

        String str = jwtUtil.generateToken(email);

        System.out.println(str);
    }

    @Test
    public void testValidate() throws Exception{

        String email = "user10@com.example";

        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);

        System.out.println(resultEmail);
    }
}
