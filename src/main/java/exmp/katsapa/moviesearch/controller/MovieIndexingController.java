package exmp.katsapa.moviesearch.controller;

import exmp.katsapa.moviesearch.service.MovieIndexingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MovieIndexingController {
    private MovieIndexingService movieindexingService;

    @GetMapping("/reindex")
    public String reindexMovies(){
        movieindexingService.reindexAllMovies();
        return "Переиндкесация завершена";
    }
}
