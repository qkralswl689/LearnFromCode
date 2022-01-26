package com.example.ex2.repository;

import com.example.entity.Memo;
import com.example.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsert(){
        System.out.println(memoRepository.getClass().getName());
    }

    // 등록
    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
           Memo memo =  Memo.builder().memoText("Memo..." + i).build();

           memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testUpdate(){

        // 객체 생성
        Memo memo = Memo.builder().mno(1L).memoText("Update Text......").build();

        System.out.println("1-------------");
        memoRepository.save(memo);
        System.out.println("2-------------");
    }

    @Test
    public void testDelete(){

        Long mno = 5L;

        memoRepository.deleteById(mno);
    }
}
