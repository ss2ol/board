package kr.co.js.board.dto;

import kr.co.js.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private Board board;
    private Long bno;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
