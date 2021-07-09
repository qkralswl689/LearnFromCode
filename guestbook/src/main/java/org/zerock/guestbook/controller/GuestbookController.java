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
    /*@GetMapping("/read")*/
    // 수정, 삭제가 가능한 페이지로 이동한 상태에서 게시물 수정하기 위해 설정
    @GetMapping({"/read","/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        
        log.info("gno : " + gno);
        
        GuestbookDTO dto = service.read(gno);
        
        model.addAttribute("dto", dto);
    }
    
    // 게시글 수정
    @PostMapping("/modify")
    // GuestbookDTO : 수정해야 하는 글의 정보를 가짐
    // PageRequestDTO : 기존의 페이지 정보를 유지하기위한것
    // RedirectAttributes : 리다이렉트로 이동
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify............................................");
        log.info("dto : " + dto );
        
        service.modify(dto);
        
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());
        
        return "redirect:/guestbook/read";
    }
    
    // 게시글 삭제
    // POST 방식으로 gno 값을 전달하고 삭제후 다시 목록의 첫페이지로 이동
    @PostMapping("/remove")
    public String remove(long gno,RedirectAttributes redirectAttributes){
        
        log.info("gno : " + gno);
        
        service.remove(gno);

        // addFlashAttribute 라는 메소드를 통해 String 데이터를 전달
        // addFlashAttribute 의 경우 데이타가 post 형식으로 전달
        // addFlashAttribute 의 경우 데이타가 한번만 사용
        redirectAttributes.addFlashAttribute("msg",gno);
        
        return "redirect:/guestbook/list";
    }




}
