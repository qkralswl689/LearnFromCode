package org.zerock.board.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@ToString(exclude = "writer")

// Member의 이메일(PK)를 FK로 참조하는 구조
public class Board extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    
    private String title;
    
    private String content;
    
    // Member 의 작성자와 연관관계 지정
    // @ManyToOne : DB 상에서 외래키의 관계로 연결된 엔티티 클래스에 설정
    // (fetch = FetchType.LAZY) : 명시적으로 Lazy 로딩 지정
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
