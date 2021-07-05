package org.zerock.guestbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2

// 자동주입
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    // 스프링 MVC는 파라미터를 자동으로 수집해 주는 기능이 있다 => 화면에서 page와 size라는 파라미터를 전달하면 PageRequestDTO객체로 자동으로 수집된다
    // Model : 결과 데이터를 화면에 전달하기 위해 사용
    public String list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

        return "/guestbook/list";
    }
    
    // 화면 보여주기
    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }
    
    // 처리 후 목록 페이지로 이동
    @PostMapping("/register")
    // RedirectAttributes 사용해 한 번만 화면에서 msg라를 변수를 사용할 수 있도록 처리
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        
        log.info("dto..." + dto);
        
        // 새로 추가된 엔티티의 번호
        
        Long gno = service.register(dto);
        
        // addFlashAttribute() : 단 한번 만 데이터를 전달하는 용도로 사용
        redirectAttributes.addFlashAttribute("msg", gno);
        
        return "redirect:/guestbook/list";
    }
    
    // GET 방식으로 gno 받아 Model에 GuestbooDTO 객체를 담아 전달하는 코드
    // 나중에 다시 목록 페이지로 돌아가는 데이터를 같이 저장하기 위해 PageRequestDTO를 파라미터로 같이 사용
    @GetMapping("/read")
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        
        log.info("gno : " + gno);
        
        GuestbookDTO dto = service.read(gno);
        
        model.addAttribute("dto", dto);
    }




}
