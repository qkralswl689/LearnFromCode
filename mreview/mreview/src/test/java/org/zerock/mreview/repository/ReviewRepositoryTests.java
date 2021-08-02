package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import javax.persistence.NamedEntityGraph;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){

        // 200개의 리뷰 등록
        IntStream.rangeClosed(1,200).forEach(i -> {

            // 영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            // 리뷰어(회원) 번호 임의의값으로 현재 DB에 존재하는 값으로 생성
            Long mid = ((long)(Math.random()*100) + 1 );
            Member member = Member.builder().mid(mid).build();

            // 영화의 평점과 리뷰의 내용을 작성해 MovieReview 객체 생성해 저장
            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()* 5 ) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }
}
