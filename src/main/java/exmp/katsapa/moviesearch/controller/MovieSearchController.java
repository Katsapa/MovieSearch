package exmp.katsapa.moviesearch.controller;

import exmp.katsapa.moviesearch.entity.Movie;
import exmp.katsapa.moviesearch.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class MovieSearchController {

    private MovieService movieService;

    @GetMapping("/")
    public String home(){
        return "search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("query") String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ){
        Page<Movie> moviesPage = movieService.searchMovieVieElasticSearch( query, page, size);
        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("query", query);
        return "search";
    }
}
