package exmp.katsapa.moviesearch.service;

import exmp.katsapa.moviesearch.entity.Movie;
import exmp.katsapa.moviesearch.entity.MovieDoc;
import exmp.katsapa.moviesearch.repository.MovieElasticSearchRepository;
import exmp.katsapa.moviesearch.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparingInt;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService {

    private MovieRepository movieRepository;

    private MovieElasticSearchRepository movieElasticSearchRepository;

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

    public Page<Movie> searchMovieVieElasticSearch(
            String query,
            int page,
            int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<MovieDoc> searchResult = movieElasticSearchRepository.searchByQuery(query, pageable);

        Map<Long, Integer> idsMap = new HashMap<>();
        List<MovieDoc> movieDocs = searchResult.getContent();
        for(int i = 0; i < movieDocs.size(); i++){
            idsMap.put(movieDocs.get(i).getId(), i);
        }

        Set<Long> ids = idsMap.keySet();
        List<Movie> moviesFromDb = movieRepository.findAllById(ids);

        moviesFromDb.sort(comparingInt(movie -> idsMap.get(movie.getId())));

        return new PageImpl<>(moviesFromDb, pageable, searchResult.getTotalElements());
    }
}