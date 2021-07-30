package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    // 자동 주입 final
    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Long rdgister(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        // 핵심! - entityToDTO()를 이용해 PageResultDTO 객체를 구성
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0],(Member)en[1],(Long)en[2]));

       // Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0],(Member) arr[1],(Long) arr[2]);
    }

    // 삭제 기능 구현, 트랜잭션 추가
    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        //댓글 부터 삭제
        replyRepository.deleteByBno(bno);

        // 게시글 삭제
        boardRepository.deleteById(bno);

    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        // getOne() : 필요한 순간까지 로딩을 지연하는 방식
        Board board = boardRepository.getOne(boardDTO.getBno());
        
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        
        boardRepository.save(board);
    }

}
