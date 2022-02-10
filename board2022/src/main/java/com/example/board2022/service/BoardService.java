package com.example.board2022.service;

import com.example.board2022.dto.BoardDTO;
import com.example.board2022.entity.Board;
import com.example.board2022.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitel())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }
}
