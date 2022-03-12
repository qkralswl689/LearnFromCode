package com.example.mreview2022.service;

import com.example.mreview2022.dto.MovieDTO;
import com.example.mreview2022.entity.Movie;
import com.example.mreview2022.entity.MovieImage;
import com.example.mreview2022.repository.MovieImageRepository;
import com.example.mreview2022.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository; // final

    private final MovieImageRepository imageRepository; // final

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String,Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movie.getMno();
    }
}
