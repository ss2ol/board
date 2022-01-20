package kr.co.js.board.controller;

import kr.co.js.board.dto.ReplyDTO;
import kr.co.js.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies/")
public class ReplyController {
    private final ReplyService replyService;

    //댓글 목록 요청 처리 메서드
    @GetMapping(value = "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>>getListByBno(@PathVariable("bno")Long bno){
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }
    //댓글 작성 요청 처리 메서드
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        Long rno = replyService.register(replyDTO);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
    //댓글 삭제 요청 처리 메서드
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("RNO:" + rno );
        replyService.remove(rno);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PostMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){
        replyService.modify(replyDTO);
        return new ResponseEntity<>("seccess",HttpStatus.OK);
    }

}
