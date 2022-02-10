package com.example.board2022.service;

import com.example.board2022.dto.BoardDTO;
import com.example.board2022.entity.Board;
import com.example.board2022.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @Autowired
    private final BoardRepository repository; //자동주입 final

    @Override
    public Long register(BoardDTO dto) {

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }
}
