package com.example.board2022.service;

import com.example.board2022.dto.BoardDTO;
import com.example.board2022.dto.PageRequestDTO;
import com.example.board2022.dto.PageResultDTO;
import com.example.board2022.entity.Board;
import com.example.board2022.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);


    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno); //삭제

    void modify(BoardDTO boardDTO); // 수정

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리
                .build();

        return boardDTO;
    }
}
