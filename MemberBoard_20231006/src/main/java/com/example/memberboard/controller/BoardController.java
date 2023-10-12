package com.example.memberboard.controller;

import com.example.memberboard.dto.BoardDTO;
import com.example.memberboard.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String save() {
        return "/boardPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        Long id = boardService.save(boardDTO);
        return "redirect:/member/mypage";
    }

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "q", required = false) String q,
                       @RequestParam(value = "page",required = false , defaultValue = "1") int page) {
        Page<BoardDTO> boardDTOList = boardService.findAll(page, type, q);

        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "/boardPages/list";
    }


}
