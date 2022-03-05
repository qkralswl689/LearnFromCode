package com.example.board2022.controller;

import com.example.board2022.dto.ReplyDTO;
import com.example.board2022.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@RequiredArgsConstructor
public class ReplyController {

    @Autowired
    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){

        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping("")
    // @RequestBody  : JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해주는 역할 -> 별도의 처리 없이도 JSON 데이터를 특정 타입의 객체로 변환해서 처리 가능
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
