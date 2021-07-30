package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    // 게시물 삭제를 위한 댓글삭제
    // @Modifying : JPQL을 이용해 update,delete를 실행하기 위해 선언
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno ")
    void deleteByBno(Long bno);

    // 게시물로 댓글 목록 가져오기
    // Board 객체를 파라미터로 받고 모든 댓글을 순번대로 가져온다
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
