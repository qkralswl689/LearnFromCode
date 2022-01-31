package com.example.guestbook2022.repository;

import com.example.guestbook2022.dto.GuestbookDTO;
import com.example.guestbook2022.dto.PageRequestDTO;
import com.example.guestbook2022.dto.PageResultDTO;
import com.example.guestbook2022.entity.Guestbook;
import com.example.guestbook2022.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test // 더미데이터생성
    public void insertDDumies(){

        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            guestbookRepository.save(guestbook);
        });
    }

    @Test // 수정시간 테스트
    public void updateTest(){

        // 300 번 선택
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        // 존재하는 번호로 테스트
        // isPresent() :  ! = null 과 같다
        if (result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    @Test // Querydsl 테스트 - 단일항목테스트(title 에 1이 포함된 내용 출력)
    public void testQueryy1(){

        // 페이징 처리
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        // findAll -> JpaRepository but Querydsl -> QuerydslPredicateExecutor 사용

        // Predicate
        // Q도메인 클래스를 사용하면 엔티티 클래스에 선언된 필드를 변수로 사용할 수 있다
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // BooleanBuilder : 쿼리의 where문에 들어가는 조건을 넣어주는 컨테이너
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1% 쿼리를 코드로 구현
        BooleanExpression expression = qGuestbook.title.contains("1");

        // 만들어진 조건 결합
        booleanBuilder.and(expression);

        // BooleanBuilder는 guestbookRepository에 추가된 QuerydslPredicateExecutor 인터페이스의 findAll()을 사용할 수 있다
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder,pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test // Querydsl 테스트 - 다중항목테스트(title 이나 content 에 1이 포함된 내용 출력)
    public void testQueryy2() {

        // 페이징 처리
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // title like %1% or content like %1% 쿼리를 코드로 구현
        BooleanExpression expression = qGuestbook.title.contains("1");
        BooleanExpression exAll = expression.or(qGuestbook.content.contains("1"));

        // 만들어진 조건 결합
        booleanBuilder.and(exAll);
        booleanBuilder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
