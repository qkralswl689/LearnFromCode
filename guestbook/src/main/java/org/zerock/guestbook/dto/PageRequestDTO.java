package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
// 화며ㅕ에 전달되는 page라는 파라미터와 size라는 파라미터를 수집하는 역할을 하는 클래스
public class PageRequestDTO {
    
    private int page;
    private int size;
    // 검색을 위한 추가
    private String type;
    private String keyword;
    
    public PageRequestDTO(){
        // 페이지 번호는 기본값을 가지는것이 좋기 때문에 기본값 설정
        this.page = 1;
        this.size = 10;
    }
    
    
    public Pageable  getPageable(Sort sort){
        // 페이지 번호가 0부터 시작한다는 점을 감안해 1페이지의 경우 0이 될 수 있도록 pagee-1하는 형태로 작성
        return PageRequest.of(page -1, size, sort);
    }
}
