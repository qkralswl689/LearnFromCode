package com.example.guestbook2022.service;

import com.example.guestbook2022.dto.GuestbookDTO;
import com.example.guestbook2022.dto.PageRequestDTO;
import com.example.guestbook2022.dto.PageResultDTO;
import com.example.guestbook2022.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();
        System.out.println(service.register(guestbookDTO));
    }


    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        /*for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }*/
        System.out.println(resultDTO);
    }

}
