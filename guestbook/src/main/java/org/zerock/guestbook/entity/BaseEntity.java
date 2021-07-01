package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 시간처리를 위한 클래스 -> 데이터 등록,수정시간을 담당하는 추상클래스
// @MappedSuperclass -> 테이블로 생성되지 않는다 => 실제테이블은 BaseEntity클래스를 상속한 엔티티의 클래스로 DB테이블이 생성된다
@MappedSuperclass
// AuditingEntityListener.class -> JPA 내부에서 entity객체가 생성/ 변경되는 것을 감지하는 역할
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    // @CreatedDate -> entity 생성시간 처리
    @CreatedDate
    // updatable = false -> entity 객체를 DB에 반영할 때 regdate 컬럼값은 변경되지 않는다
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    // @LastModifiedDate -> entity 최종 수정시간 자동으로 처리
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
