package com.danny.moviecatalogservice.resources;

import com.danny.moviecatalogservice.models.CatalogItem;
import com.danny.moviecatalogservice.models.Movie;
import com.danny.moviecatalogservice.models.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
//    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;

    @GetMapping(value = "/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

//        return Collections.singletonList(
//                new CatalogItem("Transformers", "Test", 4)
//        );

//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 4),
//                new Rating("5678", 5)
//        );

//        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/foo" + userId, UserRating.class);
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/foo" + userId, UserRating.class);

//        return ratings.stream()
        return ratings.getUserRating().stream()
//                .map(rating -> new CatalogItem("Transformers", "Test", 4))
                .map(rating -> {
                    // For each movie ID, call movie info service and get details
//                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo" + rating.getMovieId(), Movie.class);
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/foo" + rating.getMovieId(), Movie.class);

//                    Movie movie = webClientBuilder.build()
//                            .get()
////                            .uri("http://localhost:8082/movies/foo" + rating.getMovieId())
//                            .uri("http://movie-info-service/movies/foo" + rating.getMovieId())
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();

                    // Put them all together
                    return new CatalogItem(movie.getName(), "Test", rating.getRating());
                })
                .collect(Collectors.toList());

    }
}