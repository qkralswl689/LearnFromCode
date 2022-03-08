package com.example.mreview2022.repository;

import com.example.mreview2022.entity.Member;
import com.example.mreview2022.entity.Movie;
import com.example.mreview2022.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    // @EntityGraph : 엔티티의 특정한 속성을 같이 로딩하도록 표시하는 어노테이션
    // -> 특정 기능을 수행할 때만 EAGER 로딩을 하도록 지정할 수 있다
    @EntityGraph(attributePaths = {"member"},type = EntityGraph.EntityGraphType.FETCH) // Review 처리시 @EntityGraph 적용해 Member도 같이 로딩
    List<Review> findByMovie(Movie movie);

    @Modifying //insert,update,delete 쿼리에서 벌크 연산시 사용한다
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(@Param("member") Member member);
}
