package com.example.board2022.repository;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test // 게시글 100개 생성 -> 한 명의 사용자가 하나의 게시물 등록하도록
    public void insertBoard(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

}
