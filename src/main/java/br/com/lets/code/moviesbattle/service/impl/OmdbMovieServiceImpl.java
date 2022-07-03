package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.Movie;
import br.com.lets.code.moviesbattle.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OmdbMovieServiceImpl implements MovieService {

    private static final String API_KEY = "9427a3f5";
    private static final String OMDB_URL = "";

    private List<Movie> consultApiMovies() {
        List<Movie> movies = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity("", List.class);
        if(response.getStatusCode() == HttpStatus.ACCEPTED) {
            movies = response.getBody();
        }
        return movies;
    }

    @Override
    public List<Movie> getMovies() {
        List<Movie> omdbMovies = consultApiMovies();

        return omdbMovies;
    }
}
