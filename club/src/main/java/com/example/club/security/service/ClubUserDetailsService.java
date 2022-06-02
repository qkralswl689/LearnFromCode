package com.example.club.security.service;

import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;
import com.example.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    // ClubMemberRepository 주입
    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username,false);

        // 사용자 존재하지 않을경우
        if(result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        ClubMember clubMember = result.get();

        // ClubAuthMember 를 UserDetails 차입으로 처리하기위해 ClubAuthMemberDTO 타입으로 변환
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFormSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority
                                ("ROLE" + role.name())).collect(Collectors.toSet())
        );

        clubAuthMember.setName(clubAuthMember.getName());
        clubAuthMember.setFromSocial(clubAuthMember.isFromSocial());

       // System.out.println("ClubUserDetailsService loadUserByUsername " + username);
        return clubAuthMember;
    }
}
