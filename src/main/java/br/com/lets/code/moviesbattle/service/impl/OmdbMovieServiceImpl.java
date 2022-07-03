package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.Movie;
import br.com.lets.code.moviesbattle.service.MovieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class OmdbMovieServiceImpl implements MovieService {

    private static final String API_KEY = "9427a3f5";
    private static final String OMDB_URL = "http://www.omdbapi.com/";

    private List<Movie> consultApiMovies(String movie) {
        StringBuilder url = new StringBuilder(OMDB_URL);
        url.append("?apikey=")
                .append(API_KEY)
                .append("&s=")
                .append(movie);

        List<Movie> movies = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap<String, Object> response = restTemplate.getForObject(url.toString(), LinkedHashMap.class);
        if(response != null && response.get("Search") != null) {
            ObjectMapper mapper = new ObjectMapper();
            movies = mapper.convertValue(response.get("Search"), new TypeReference<List<Movie>>() {});
        }
        return movies;
    }

    private Movie getMovieByIdApi(String movieId) {
        StringBuilder url = new StringBuilder(OMDB_URL);
        url.append("?apikey=")
                .append(API_KEY)
                .append("&i=")
                .append(movieId);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url.toString(), Movie.class);
    }

    @Override
    public List<Movie> getMovies() {
        List<String> movies = Arrays.asList("Batman", "Superman"); //getMoviesList();
        List<Movie> listMovies = new ArrayList<>();
        if(movies != null) {
            listMovies = movies.parallelStream()
                    .map(this::consultApiMovies)
                    .flatMap(List::stream)
                    .toList();
        }
        return listMovies;
    }

    @Override
    public Movie getMovieById(String idMovie1) {
        return getMovieByIdApi(idMovie1);
    }

    private List<String> getMoviesList() {
        List<String> moviesList;
        try {
            String movies = Files.readString(Paths.get(getClass().getResource("moviesList.txt").toURI()));
            String [] arrMovies = movies.split(",");
            moviesList = Arrays.asList(arrMovies);
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return moviesList;
    }

}
