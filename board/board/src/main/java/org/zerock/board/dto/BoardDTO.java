package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    // 작성자의 이메일(id)
    private String writerEmail;

    // 작성자의 이름
    private String writerName;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    // 해당 게시글의 댓글 수
    private int replyCount;
}
