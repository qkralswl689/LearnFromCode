package com.example.mreview2022.repository;

import com.example.mreview2022.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r" + i + "@zerock.org")
                    .pw("1111")
                    .nickname("reviewer" + i).build();

            memberRepository.save(member);
        });
    }

    @Commit // 테스트코드 실행성공 후 DB에서도 업데이트 된 결과를 확인하기위해 사용
    @Transactional
    @Test
    public void testDeleteMember(){

        Long mid = 1L; //Member의 mid

        Member member = Member.builder().mid(mid).build();

        // 순서 주의
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
