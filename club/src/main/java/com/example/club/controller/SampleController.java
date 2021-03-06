package com.example.club.controller;

import com.example.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {

    // 로그인을 하지 않은 사용자도 접근 가능
    @GetMapping("/all")
    public void exAll(){
        System.out.println("exALl");
    }

    // 로그인한 사용자만 접근 가능
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        System.out.println("exMember");
        System.out.println(clubAuthMember);
    }

    // 관리자 권한이 있는 사용자만 접근가능
    @GetMapping("/admin")
    public void exAdmin(){
        System.out.println("exAdmin");
    }
}
