package kr.co.js.board.repository;

import kr.co.js.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    //글번호를 가지고 댓글을 삭제하는 메서드
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno")
    public void deleteByBno(Long bno);

}
