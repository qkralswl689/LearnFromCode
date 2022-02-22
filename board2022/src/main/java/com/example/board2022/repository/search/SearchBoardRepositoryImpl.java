package com.example.board2022.repository.search;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.QBoard;
import com.example.board2022.entity.QMember;
import com.example.board2022.entity.QReply;
import com.querydsl.core.Tuple;
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
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member.email,reply.count());
        tuple.groupBy(board);

        List<Tuple> result = tuple.fetch();

        return null;
    }
}
