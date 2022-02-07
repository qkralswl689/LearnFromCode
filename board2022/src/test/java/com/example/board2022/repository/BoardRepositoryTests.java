package com.example.board2022.repository;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.Member;
import com.example.board2022.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;

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

    @Test
    @Transactional // 필요한 순간 다시 DB 연결
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L); // DB에 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("---------------");
        System.out.println(Arrays.toString(arr));
    }

}
