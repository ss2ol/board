package kr.co.js.board;

import kr.co.js.board.dto.BoardDTO;
import kr.co.js.board.dto.PageRequestDTO;
import kr.co.js.board.dto.PageResultDTO;
import kr.co.js.board.dto.ReplyDTO;
import kr.co.js.board.service.BoardService;
import kr.co.js.board.service.ReplyService;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private BoardService boardService;

    //@Test
    public void testInsert(){
        BoardDTO dto = BoardDTO.builder()
                .title("삽입 테스트")
                .content("삽입을 테스트 합니다.")
                .writerEmail("user1@gmail.com")
                .build();

        Long bno = boardService.register(dto);
        System.out.println("삽입한 글 번호:" + bno);
    }

    //@Test
    public void testList(){
        PageRequestDTO dto = new PageRequestDTO();
        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(dto);
        for(BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
        System.out.println(result.getPageList());
    }

    // @Test
    public void testGet(){
       BoardDTO dto = boardService.get(100L);
       System.out.println(dto);

    }

   // @Test
    public void testDelete(){
        boardService.removeWithReplies(12L);
    }

    //@Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("수정한 제목")
                .content("수정한 내용")
                .build();

        boardService.modify(dto);
    }

    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetReplies(){
        List<ReplyDTO> list = replyService.getList(45L);
        System.out.println(list);
    }



}
