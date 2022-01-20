package kr.co.js.board.repository;

import kr.co.js.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    public Board search();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);


}
