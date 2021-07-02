package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
// 다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해 DTO와 EN이라는 타입을 지정한다 => DTO 와 Entity타입을 의미
public class PageResultDTO<DTO, EN> {
    
    // List타입으로 DTO객체들 보관 -> Page<Entity>의 내용물 중 엔티티 객체를 DTO로 변환하는 기능 필요
    private List<DTO> dtoList;
    
    // Function<EN, DTO> fn) -> 엔티티 객체들을 DTO로 변환해주는 기능
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }
}
