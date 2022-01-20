package kr.co.js.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.co.js.board.entity.Board;
import kr.co.js.board.entity.QBoard;
import kr.co.js.board.entity.QMember;
import kr.co.js.board.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search() {
        log.info("메서드 호출");
        //Board 엔티티에 쿼리를 수행하기 위한 객체 생성
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        //Member Entity와 join
        //Board의 writer가 Member를 참조
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        //Reply와 join
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        //필요한 항목 추출
        //jpqlQuery.select(board,member.email,reply.count()).groupBy(board);

        //결과를 Board로 받음
        //List<Board> result = jpqlQuery.fetch();

        //
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);
        List<Tuple> result = tuple.fetch();

        System.out.println(result);
        return null;


    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        //3개의 Entity join
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        //Member Entity와 join
        //Board의 writer가 Member를 참조
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        //Reply와 join
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //Board와 Member.email 그리고 Reply의 count를 가져오는 쿼리 생성
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        //동적쿼리 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //조건 생성
        BooleanExpression expression = board.bno.gt(0L);
        //조건추가
        booleanBuilder.and(expression);

        //t : title 검색
        //c: content
        //w: writer
        //tc: title 또는 content
        //tcw: title 또는 content 또는 writer
        if (type != null) {
          String [] typeAr = type.split("");
          BooleanBuilder conditionBuilder = new BooleanBuilder();
          for(String t:typeAr){
              switch (t){
                  case "t":
                      conditionBuilder.or(board.title.contains(keyword));
                      break;
                  case "c":
                      conditionBuilder.or(board.content.contains(keyword));
                      break;
                  case "w":
                      break;
              }
          }
          booleanBuilder.and(conditionBuilder);
        }
        //조건검색추가
        tuple.where(booleanBuilder);

        //정렬 추가
        Sort sort = pageable.getSort();

        sort.stream().forEach((order -> {
            Order direction = order.isAscending()?Order.ASC: Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");
            tuple.orderBy(new OrderSpecifier<>(direction,orderByExpression.get(prop)));

        }));
        //그룹화
        tuple.groupBy(board);
        //페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        //데이터 가져오기
        List<Tuple> result = tuple.fetch();

        //데이터 리턴
        return new PageImpl<Object[]>(
                result.stream().map(t->t.toArray()).collect(Collectors.toList()),
                pageable,tuple.fetchCount());



    }


}
