package com.example.board2022.repository.search;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.QBoard;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() { // QuerydslRepositorySupport는 생성자가 존재하므로 클래스 내에서 super()를 이용해 호출해야 한다
        super(Board.class);
    }

    @Override
    public Board search1() {

        // log.info("search1............");

        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.select(board).where(board.bno.eq(1L));

        List<Board> result = jpqlQuery.fetch();

        return null;
    }
}
