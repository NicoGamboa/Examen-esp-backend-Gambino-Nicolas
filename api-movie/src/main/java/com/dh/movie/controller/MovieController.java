package com.dh.movie.controller;

import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {


    private final MovieService movieService;
    private final NewMovieEventProducer newMovieEventProducer;

    public MovieController(MovieService movieService, NewMovieEventProducer newMovieEventProducer) {
        this.movieService = movieService;
        this.newMovieEventProducer = newMovieEventProducer;
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(movieService.findByGenre(genre));
    }

    @PostMapping("/save")
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        NewMovieEventProducer.Data data = new NewMovieEventProducer.Data();
        data.setId(movie.getId());
        data.setGenre(movie.getGenre());
        data.setName(movie.getName());
        data.setUrlStream(movie.getUrlStream());
        newMovieEventProducer.newEventMovie(data);
        return ResponseEntity.ok().body(movieService.save(movie));
    }
}
