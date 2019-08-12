package com.danny.ratingsdataservice.resources;

import com.danny.ratingsdataservice.models.Rating;
import com.danny.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @GetMapping(value = "/{movieId}")
    public Rating getCatalog(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping(value = "/users/{userId}")
    public UserRating getRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 5)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }
}