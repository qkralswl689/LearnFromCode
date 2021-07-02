package org.zerock.guestbook.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." +i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    // 수정 테스트
    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title...");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }
    
    // 단일 항목 검색 테스트 -< 페이지 처리와 검색처리를 동시에
    @Test
    public void testQuery1(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        // 1. 가장 먼저 동적으로 처리하기 위해 Q도메인 클래스를 얻어온다 
        // -> Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title, content 같은 필드들을 변수로 활용할 수 있다
        QGuestbook qGuestbook = QGuestbook.guestbook;
        
        String keyword = "1";

        // 2. BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        // 3. 원하는 조건은 필드 값과 같이 결합해서 생성 , * BooleanBuilder 안에 들어가는 값은  com.querydsl.types.Predicate 타입이여야 한다
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        
        // 4. 만들어진 조건은 where문에 and나 or같은 키워드와 결합시킨다
        builder.and(expression);

        // 5. BooleanBuilder는 GuestbookRepository에 추가된 QuerydslPredicateExcutor 인터페이스의 findAll()을 사용할 수 있다
        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    // 다중 항목 검색 테스트
    @Test
    public void testQuery2(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);

        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
