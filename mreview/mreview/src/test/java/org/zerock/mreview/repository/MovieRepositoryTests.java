package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;

    // 영화, 영화이미지 추가 테스트코드
    @Commit
    @Transactional
    @Test
    public void insertMovies(){

             
        IntStream.rangeClosed(1,100).forEach(i -> {
            Movie movie = Movie.builder().title("Movie...." + i).build();

            System.out.println("-----------------------------------------------");

            // 영화와 영화의 이미지들은 같은 시점에 imsert처리가 되어야 하기 때문에 Movie객체를 우선 save()해준다
            // -> save()실행 후 Movie객체는 PK에 해당하는 mno값이 할당되므로 이를 이요해 영화의 이미지들을 추가한다
            movieRepository.save(movie);
            
            // 영화의 이미지들은 최대 5개까지 임의로 저장된다
            // 1,2,3,4
            int count = (int)(Math.random() * 5) + 1;

            for(int j = 0; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg").build();

                imageRepository.save(movieImage);

                System.out.println("==========================================");
            }
        });
    }

    // 영화 와 리뷰 를 이용해 페이징 처리
    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

}
