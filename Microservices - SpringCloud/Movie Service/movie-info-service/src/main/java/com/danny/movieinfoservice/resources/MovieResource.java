package com.danny.movieinfoservice.resources;

import com.danny.movieinfoservice.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @GetMapping(value = "/{movieId}")
    public Movie getCatalog(@PathVariable("movieId") String movieId) {
        return new Movie(movieId, "Test name");
    }
}