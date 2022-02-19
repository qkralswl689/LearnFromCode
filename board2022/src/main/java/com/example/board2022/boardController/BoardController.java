package com.example.board2022.boardController;

import com.example.board2022.dto.BoardDTO;
import com.example.board2022.dto.PageRequestDTO;
import com.example.board2022.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor // 자동주입
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
    }

    @PostMapping("/register")
    public String regesterPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

/*    @GetMapping("/read")
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,Long bno, Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto",boardDTO);
    }*/

    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO,Long bno,Model model){

        BoardDTO boardDTO = boardService.get(bno);

        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("remove")
    public String remove(long bno,RedirectAttributes redirectAttributes){

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,RedirectAttributes redirectAttributes){

        boardService.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";
    }
}
