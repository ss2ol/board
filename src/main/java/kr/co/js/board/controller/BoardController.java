package kr.co.js.board.controller;

import kr.co.js.board.dto.BoardDTO;
import kr.co.js.board.dto.PageRequestDTO;
import kr.co.js.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
//공통URL설정
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("register")
    public void register(){
    }

    @PostMapping("register")
    public String registerPost(BoardDTO dto, RedirectAttributes rattr){
        Long bno = boardService.register(dto);
        rattr.addFlashAttribute("msg", bno+"등록");
        return "redirect:/board/list";
    }

    @GetMapping({"read", "modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                     Long bno, Model model){
        BoardDTO dto = boardService.get(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("remove")
    public String remove(long bno, RedirectAttributes rattr){
        boardService.removeWithReplies(bno);
        //출력할 메세지 저장
        rattr.addFlashAttribute("msg", bno +"삭제");
               return "redirect:/board/list";
    }

    @PostMapping("modify")
    public String modify(BoardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes rattr){
        boardService.modify(dto);
        rattr.addAttribute("page",requestDTO.getPage());
        rattr.addAttribute("type",requestDTO.getType());
        rattr.addAttribute("keyword",requestDTO.getKeyword());
        rattr.addAttribute("bno",dto.getBno());
        return "redirect:/board/read";
    }


}




