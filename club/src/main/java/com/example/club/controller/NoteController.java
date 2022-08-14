package com.example.club.controller;

import com.example.club.dto.NoteDTO;
import com.example.club.service.Noteservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final Noteservice noteservice; //final

    // 새로운 NOTE 등록할 수 있는 기능
    @PostMapping(value = "")
                                        // @RequestBody : JSON 데이터를 받아 NoteDTO로 변환
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){

        Long num = noteservice.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    // 특정한 번호의 Note를 확인할 수 있는 기능 추가
    @GetMapping(value = "/{num}" , produces = MediaType.APPLICATION_JSON_VALUE)
                                    // @PathVariable을 사용해 경로에 있는 Note의 num을 얻어 해당 Note 정보를 반환한다.
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){

        return new ResponseEntity<>(noteservice.get(num), HttpStatus.OK);
    }

    // 특정 이메일을 가진 회원이 작성한 모든 Note를 조회할 수 있는 기능
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    // 파라미터로 전달되는 이메일 주소를 통해 해당회원이 작성한 모든 Note에 대한 정보를 JSON으로 반환
   public ResponseEntity<List<NoteDTO>> getList(String email){
        return new ResponseEntity<>(noteservice.getAllWithWriter(email), HttpStatus.OK);
    }
}
