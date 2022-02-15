package com.example.board2022.service;

import com.example.board2022.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .titel("Test...")
                .content("Test....")
                .writerEmail("user55@aaa.com") // 현재 DB에 존재하는 회원이메일
                .build();

        Long bno = boardService.register(dto);
    }
}
