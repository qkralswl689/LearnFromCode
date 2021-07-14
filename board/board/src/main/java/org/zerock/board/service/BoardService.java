package org.zerock.board.service;

import org.springframework.data.repository.query.Param;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long rdgister(BoardDTO dto);

    // 목록처리
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 게시물 조회 -> 파라미터로 게시물의 번호(bno)받아 처리
    BoardDTO get(Long bno);

    // 게시물 삭제
    void removeWithReplies(Long bno);

    // DTO 가 연관관계를 가진 Board 엔티티 객체와 Member엔티티 객체를 구성해야 하므로 
    // 내부적으로 Member 엔티티를 처리하는 과정을 거쳐야 한다
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

    // JPQL 의 결과로 나오는 Object[] 을 DTO 타입으로 변환
    // Board,Member,댓글의수를 파라미터로 전달받고 이를 이용해 BoardDTO 객체를 생성할 수 있도록 처리
    default BoardDTO entityToDTO(Board board, Member member, Long replycount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                // long으로 나오므로 int로 처리해야한다
                .replyCount(replycount.intValue())
                .build();

        return boardDTO;

    }

}
