package com.example.ex2.repository;

import com.example.entity.Memo;
import com.example.repository.MemoRepository;
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

    @Test
    public void testPageDefault(){

        // 정렬
        Sort sort = Sort.by("mno").descending();

        Sort sort2 = Sort.by("memoText").ascending();

        Sort sortAll = sort.and(sort2); // and를 이용한 연결


        // 1페이지 10개
        Pageable pageable = PageRequest.of(0,10,sort);

        Pageable pageable2 = PageRequest.of(0,10,sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

    }


}
