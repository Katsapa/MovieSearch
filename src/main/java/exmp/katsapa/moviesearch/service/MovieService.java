package exmp.katsapa.moviesearch.service;

import exmp.katsapa.moviesearch.entity.Movie;
import exmp.katsapa.moviesearch.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService {

    private MovieRepository movieRepository;

    public Page<Movie> searchMovie(String query, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> result = movieRepository.searchByQuery(query, pageable);

        result.getContent().forEach(m ->
                log.info("actors type: {} | value: {}",
                        m.getActors().getClass().getSimpleName(),
                        m.getActors())
        );

        return result;
    }
}