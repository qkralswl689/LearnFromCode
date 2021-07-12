package org.zerock.board.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@ToString
// 이메일을 사용자의 아이디 대신에 사용
public class Member extends BaseEntity{

    @Id
    private String email;

    private String password;

    private String name;
}
