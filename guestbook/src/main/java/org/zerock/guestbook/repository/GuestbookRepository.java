package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.zerock.guestbook.entity.Guestbook;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> , QuerydslPredicateExecutor<Guestbook> {
}
