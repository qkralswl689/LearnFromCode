package com.example.board2022.service;

import com.example.board2022.dto.BoardDTO;
import com.example.board2022.dto.PageRequestDTO;
import com.example.board2022.dto.PageResultDTO;
import com.example.board2022.entity.Board;
import com.example.board2022.entity.Member;
import com.example.board2022.repository.BoardRepository;
import com.example.board2022.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardRepository repository; //자동주입 final

    @Autowired
    private final ReplyRepository replyRepository; //추가

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[],BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));


        return new PageResultDTO<>(result,fn);
    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0],(Member)arr[1],(Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) { // 삭제 구현, 트랜잭션 추가

        // 댓글부터 삭제
        replyRepository.deleteByBno(bno);

        // 게시글 삭제
        repository.deleteById(bno);

    }
}
