package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

// toString() 호출시 다른 엔티티 사용하지 않도록 속성 지정
// Review 클래스 -> Movie, Member 를 양쪽으로 참조하는 구조
@ToString(exclude = {"movie","member"})
public class Review extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    
    private int grade;
    
    private String text;
}
