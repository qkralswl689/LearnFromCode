package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@ToString(exclude = "board")
// 댓글! -> 회원이 아닌 사람도 댓글을 남길 수 있다 
public class Reply extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    
    private String text;
    
    private String replyer;
    
    // Board와 연관관계 작성
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
