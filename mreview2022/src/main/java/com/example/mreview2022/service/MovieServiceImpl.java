package com.example.mreview2022.service;

import antlr.PreservingFileWriter;
import com.example.mreview2022.dto.MovieDTO;
import com.example.mreview2022.dto.PageRequestDTO;
import com.example.mreview2022.dto.PageResultDTO;
import com.example.mreview2022.entity.Movie;
import com.example.mreview2022.entity.MovieImage;
import com.example.mreview2022.repository.MovieImageRepository;
import com.example.mreview2022.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @Autowired
    private final MovieRepository movieRepository; // final

    @Autowired
    private final MovieImageRepository imageRepository; // final

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie) arr[0],
                (List<MovieImage>) (Arrays.asList((MovieImage) arr[1])),
                (Double) arr[2],
                (Long) arr[3])
        );
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0] ; // Movie ???????????? ?????? ?????? ?????? - ?????? Row??? ????????? ?????????

        List<MovieImage> movieImageList = new ArrayList<>(); // ????????? ????????????????????? MovieImage ?????? ??????

        result.forEach(arr ->{
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2]; //?????? ?????? - ?????? Row??? ????????? ???
        Long reviewCnt = (Long) result.get(0)[3]; //?????? ?????? - ?????? Row??? ????????? ???

        return entitiesToDTO(movie,movieImageList,avg,reviewCnt);
    }


}
