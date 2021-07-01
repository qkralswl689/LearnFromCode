package org.zerock.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;


import java.util.stream.IntStream;

@SpringBootTest

public class GuestbookRepositoryTests {

    @Autowired
  /*  GuestbookRepository guestbookRepository;*/

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("user" + (i %10))
                    .build();
           /* System.out.println(guestbookRepository.saveAll(guestbook));*/
        });
    }

}
