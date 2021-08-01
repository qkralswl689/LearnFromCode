package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

// 연관 관계 주의
@ToString(exclude = "movie")
// 단방향 참조로 처리, @Query로 left join 을 사용
// 나중에 사용할 이미지에 대한 정보 기록 클래스
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    // java.util.UUID를 이용해 고유한 번호 생성해 사용
    private String uuid;

    private String imgName;

    // 이미지의 저장 경로(path) -> 년/월/일 폴더 구조를 의미
    private String path;

    // 테이블로 생성될 때 movie테이블이 PK를 가지고 movie_image테이블은 FK를 가지게 되므로 @ManyToOne을 적용해 이를 표시한다
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
