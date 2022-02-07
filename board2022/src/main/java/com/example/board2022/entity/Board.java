package com.example.board2022.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") // exclude : toString 대상에서 제외한다
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    // LAZY : 필요할 때만 사용, LAZY 사용하면 @ToString(exclude) 무조건 사용!
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;


}
