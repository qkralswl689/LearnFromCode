package com.example.mreview2022.repository;

import com.example.mreview2022.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
