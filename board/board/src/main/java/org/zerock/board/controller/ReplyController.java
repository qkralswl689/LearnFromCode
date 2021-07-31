package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.service.ReplyService;

import java.util.List;

// @RestController : 댓글 데이터를 JSON 으로 만들어 처리
// -> 모든 메서드의 리턴 타입은 기본으로 JSON을 사용한다
@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    // 자동주입을 위해 final 사용
    private final ReplyService replyService;

    // {bno} -> 메서드 내에서 @PathVariable 이라는 것으로 처리
    // -> 브라우저에서 특정 게시물 번호로 조회할 때 bno 라는 데이터를 변수로 처리하는것이 가능해진다

    @GetMapping(value = "/board/{bno}",produces = MediaType.APPLICATION_JSON_VALUE)
    // ResponseEntity : 메서드 반환타입
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno")Long bno){

        log.info("bno : " + bno);

        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }
    @PostMapping("")
    // @RequestBody : JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해주는 역할
    // -> 개발시에는 별도의 처리 없이도 JSON 데이터를 측정 타입의 객체로 변환해 처리할 수 있다
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno,HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){

        log.info("RNO : " + rno);

        // 댓글의번호(rno)으로 삭제
        replyService.remove(rno);

        // 문자열로 결과 전송
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 수정 -> PutMappting으로 댓글 데이터를 ReplyDTO 객체로 변환해 처리
    @PutMapping("/{rno}")
    public ResponseEntity<String> modift(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
