package com.example.board2022.repository.search;

import com.example.board2022.entity.Board;
import com.example.board2022.entity.QBoard;
import com.example.board2022.entity.QMember;
import com.example.board2022.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;


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

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board,member,reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split("");

            // 검색 조건 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr) {
                switch (t){
                    case "t" :
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w" :
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c" :
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        // order by
        Sort sort = pageable.getSort();

        //tuple.orderBy(board.bno.desc());
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC:Order.DESC;

            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");

            tuple.orderBy(new OrderSpecifier(direction,orderByExpression.get(prop)));

        });

        tuple.groupBy(board);

        //page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()),pageable,count);
    }
}
