package com.example.board2022.repository;

import com.example.board2022.entity.Board;
import com.example.board2022.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {

    // 한개의 로우(object) 내에 Object[]로 나옴
    // Board를 사용하고 있지만 Member를 같이 조회해야 하는 상황에서 사용
    @Query("select b , w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    // 연관관계가 없는 엔티티 조인 처리 (ON 사용)
    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b " +
            " GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // 목록 화면에 필요한 데이터


    @Query("SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
