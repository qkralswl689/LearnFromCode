package com.example.guestbook2022.controller;

import com.example.guestbook2022.dto.GuestbookDTO;
import com.example.guestbook2022.dto.PageRequestDTO;
import com.example.guestbook2022.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor // 자동주입
@RequestMapping("/guestbook")
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDTO pageRequestDTO , Model model){

        // 실제로 model에 추가되는 데이터 : PageResultDTO
        // => Model을 이용해 GuestbookServiecImpl에서 반환하는 PageResultDTO를 result 라는 이름으로 전달
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        // addFlashAttribute 새로만들어진 엔티티의 번호 추가
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long gno,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,Model model){

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes){

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("modify")
    public String modify(GuestbookDTO dto,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,RedirectAttributes redirectAttributes){
        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }
    /*@GetMapping({"/","/list"})
    public String list(){
        return "/guestbook/list";
    }*/
}
