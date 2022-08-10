package com.example.club.security.service;

import com.example.club.entity.ClubMember;
import com.example.club.entity.ClubMemberRole;
import com.example.club.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {


    private final ClubMemberRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2User oAuth2User =  super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((k,v) ->{

        });

        String email = null;

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        ClubMember member = saveSocailMember(email);

        return oAuth2User;
    }

    private ClubMember saveSocailMember(String email){

        // 기존에 동일한 이메일로 가입한 회원이 있는 경우에는 조회만
        Optional<ClubMember> result = repository.findByEmail(email,true);

        if(result.isPresent()){
            return result.get();
        }

        // 없다면 회원 추가 PW는 1111 이름은 그냥 이메일주소

        ClubMember clubMember = ClubMember.builder().email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .formSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        repository.save(clubMember);

        return clubMember;
    }
}
