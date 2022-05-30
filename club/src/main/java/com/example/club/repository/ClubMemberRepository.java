package com.example.club.repository;

import com.example.club.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember,String> {

    //  @EntityGraph : left outer join
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.formSocial = :social and m.email =:email")
    Optional<ClubMember> findByEmail(@Param("email") String email, @Param("social") boolean social);
}
