package com.example.ex3.controller;

import com.example.ex3.dto.SampleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
public class Samplecontroller {

    @GetMapping("/ex1")
    public void ex1(){

    }

    @GetMapping("/ex2")
    public void ex2(Model model){

        SampleDTO dto = SampleDTO.builder()
                .regTime(LocalDateTime.now())
                .build();

        // 객체 생성
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i ->
                dto.toBuilder().sno(i).first("first..." + i).last("last..." + i).build()
        ).collect(Collectors.toList());

        model.addAttribute("list",list);

    }

    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes){

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result","success");
        redirectAttributes.addFlashAttribute("dto",dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){}

    @GetMapping({"/ex2","/exLink"})

    public void exModel(Model model) {

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(100L)
                    .first("First..100")
                    .last("Last..100")
                    .regTime(LocalDateTime.now())
                    .build();

            return dto;
        }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }
}
