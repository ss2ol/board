package kr.co.js.board.service;

import kr.co.js.board.dto.ReplyDTO;
import kr.co.js.board.entity.Board;
import kr.co.js.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    //데이터 삽입을 위한 메서드
    public Long register (ReplyDTO replyDTO);
    //수정
    public void modify(ReplyDTO replyDTO);
    //삭제
    public void remove(Long rno);
    //목록가져오기
    public List<ReplyDTO> getList(Long bno);

    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    //ReplyEntity를 ReplyDTO로 변환
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
                return replyDTO;
    }

}
