package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2

// 의존성 자동 주입 (repository)
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    // 반드시 final로 선언!
    // JPA 처리를 위해 GuestbookRepository 주입
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO----------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        // 저장
        repository.save(entity);

        // 엔티티가 가지는 gno값 반환
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        // entityToDto()를 이용해 Function생성하고 이를 PageResultDTO로 구성
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        // PageResultDTO : JPA의 처리결과인 Page<Entity> 와 Function을 전달해 엔티티 객체들을 DTO의 리스트로 변환하고 화면에 페이지 처리와 필요한 값들을 생성
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        // findById() 를 통해 엔티티 객체 가져오기
        Optional<Guestbook> result = repository.findById(gno);

        // entityToDto()를 이용해 엔티티 객체를 DTO로 변환 해서 반환
        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {

        // 업데이트(수정) 하는 항목은 "제목", "내용" 만 수정하고 다시 저장하는 방식

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }


}
