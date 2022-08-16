package com.example.club.service;

import com.example.club.dto.NoteDTO;
import com.example.club.entity.Note;
import com.example.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServideImpl implements Noteservice{

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {

        Note note = dtoToEntity(noteDTO);

        noteRepository.save(note);

        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {

        Optional<Note> result = noteRepository.getWithWriter(num);

        if(result.isPresent()){

            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {

        Long num = noteDTO.getNum();

        Optional<Note> result = noteRepository.findById(num);

        if(result.isPresent()){

            Note note = result.get();

            note.changeTitle(noteDTO.getTitle());

            note.changeContent(noteDTO.getContent());

            noteRepository.save(note);
        }

    }

    @Override
    public void remove(Long num) {

        noteRepository.deleteById(num);

    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {

        List<Note> noteList = noteRepository.getList(writerEmail);

        return noteList.stream().map(note -> entityToDTO(note)).collect(Collectors.toList());
    }
}
