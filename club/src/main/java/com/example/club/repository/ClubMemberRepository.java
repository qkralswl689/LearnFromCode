package com.example.club.repository;

import com.example.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember,String> {
}
