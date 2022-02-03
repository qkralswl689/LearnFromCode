package com.example.board2022.repository;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test // 임의의 게시글을 대상으로 댓글추가(300개)
    public void insertReply(){

        IntStream.rangeClosed(1,300).forEach(i -> {
            // 1부터 100까지 임의의 번호
            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply...." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        });
    }
}
