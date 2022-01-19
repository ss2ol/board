package kr.co.js.board.controller;

import kr.co.js.board.dto.PageRequestDTO;
import kr.co.js.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;

    @GetMapping("/")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result",boardService.getList(pageRequestDTO));

        return "/board/list";
    }



}
