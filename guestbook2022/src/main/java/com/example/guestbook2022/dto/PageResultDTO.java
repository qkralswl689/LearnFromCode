package com.example.guestbook2022.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// 서버에서 화면으로 갈때 사용
@Data
            // 다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해 DTO 와 EN(entity) 이라는 타입을 지정한다
public class PageResultDTO <DTO,EN>{

    private List<DTO> dtoList;
                                           // Function<EN,DTO> : 엔티티 객체들을 DTO로 변환해주는 기능
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
