package kr.co.js.board.repository;

import kr.co.js.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> ,SearchBoardRepository {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno")Long bno);


    @Query("select b,r from Board b left join Reply r ON r.board = b where b.bno = :bno")
    List<Object []> getBoardWithReply(@Param("bno")Long bno);

    @Query(value = "select b, w, count(r) from Board b LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board =b GROUP BY b",countQuery = "select count(b) from Board b")
    Page<Object []> getBoardWithReplyCount(Pageable pageable);

    //게시글 상세보기를 위한 메서드
    @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno")Long bno);

}
