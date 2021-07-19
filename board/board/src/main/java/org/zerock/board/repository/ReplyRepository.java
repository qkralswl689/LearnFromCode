package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    // 게시물 삭제를 위한 댓글삭제
    // @Modifying : JPQL을 이용해 update,delete를 실행하기 위해 선언
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno ")
    void deleteByBno(Long bno);
}
