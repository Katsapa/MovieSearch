package exmp.katsapa.moviesearch.service;

import exmp.katsapa.moviesearch.entity.Movie;
import exmp.katsapa.moviesearch.entity.MovieDoc;
import exmp.katsapa.moviesearch.repository.MovieElasticSearchRepository;
import exmp.katsapa.moviesearch.repository.MovieRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class MovieIndexingService {

    private MovieRepository movieRepository;

    private MovieElasticSearchRepository movieElasticSearchRepository;

    @Transactional(readOnly = true)
    public void reindexAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        movieElasticSearchRepository.saveAll(
                movies.stream().map(
                        movie -> new MovieDoc(
                                movie.getId(),
                                movie.getMovie(),
                                movie.getOverview()
                        )
                ).toList()
        );
    }
}
