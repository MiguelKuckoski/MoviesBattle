package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.Movie;
import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;
import br.com.lets.code.moviesbattle.dto.restApi.MovieApi;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;
import br.com.lets.code.moviesbattle.repository.GameRoundRepository;
import br.com.lets.code.moviesbattle.service.GameRoundService;
import br.com.lets.code.moviesbattle.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GameRoundServiceImpl implements GameRoundService {

    private GameRoundRepository gameRoundRepository;

    private MovieService movieService;
    public GameRoundServiceImpl(GameRoundRepository gameRoundRepository, MovieService movieService) {
        this.gameRoundRepository = gameRoundRepository;
        this.movieService = movieService;
        startMoviesList();
    }

    private List<Movie> moviesList;

    private void startMoviesList() {
        moviesList = movieService.getMovies();
    }

    @Override
    public Optional<GameRound> getCurrentRound(Game game) {
        GameRound gameRound = gameRoundRepository.getCurrentRoundByGame(game);
        return Optional.ofNullable(gameRound);
    }

    @Override
    public GameRound getNewGameRound(Game game) {
        GameRound validGameRound = null;
        do{
            GameRound gameRound = newRandomRound();
            if(!gameRound.getIdMovie1().equals(gameRound.getIdMovie2())) {
                Optional<GameRound> hasCurrentMoviesGameCombination =
                        gameRoundRepository.hasCurrentMoviesGameCombination(game,
                                gameRound.getIdMovie1(),
                                gameRound.getIdMovie2());

                if(!hasCurrentMoviesGameCombination.isPresent()) {
                    validGameRound = gameRound;
                }
            }
        }while(validGameRound == null);
        fillGameRound(validGameRound, game);

        gameRoundRepository.save(validGameRound);

        return validGameRound;
    }

    private void fillGameRound(GameRound validGameRound, Game game) {
        validGameRound.setGame(game);

        Movie movie1 = movieService.getMovieById(validGameRound.getIdMovie1());
        Movie movie2 = movieService.getMovieById(validGameRound.getIdMovie2());

        String correctAwnser = getBiggerRating(movie1, movie2);
        validGameRound.setCorrectAwnser(correctAwnser);
    }

    private GameRound  newRandomRound() {

        GameRound gameRound = new GameRound();
        Movie movie1 = getRandomMovie();
        Movie movie2 = getRandomMovie();

        gameRound.setIdMovie1(movie1.getImdbID());
        gameRound.setNameMovie1(movie1.getTitle());

        gameRound.setIdMovie2(movie2.getImdbID());
        gameRound.setNameMovie2(movie2.getTitle());
        return gameRound;
    }

    public Movie getRandomMovie() {
        Random rand = new Random();
        return moviesList.get(rand.nextInt(moviesList.size()));
    }

    private String getBiggerRating(Movie movie1, Movie movie2) {
        String votesMovie1 = movie1.getImdbVotes() == null? "0" : movie1.getImdbVotes().replace(",", ".");
        String votesMovie2 = movie2.getImdbVotes() == null? "0" : movie2.getImdbVotes().replace(",", ".");

        Double rattingMovie1 = Double.parseDouble(votesMovie1) * movie1.getImdbRating();
        Double rattingMovie2 = Double.parseDouble(votesMovie2) * movie2.getImdbRating();

        if(rattingMovie1 > rattingMovie2) {
            return movie1.getImdbID();
        } else {
            return movie2.getImdbID();
        }
    }

    @Override
    public GameRoundApi gameRoundToDto(GameRound gameRound) {
        GameRoundApi gameRoundApi = new GameRoundApi();
        gameRoundApi.setRoundId(gameRound.getRoundId());

        MovieApi movie1 = new MovieApi();
        movie1.setTitle(gameRound.getNameMovie1());
        movie1.setImdbID(gameRound.getIdMovie1());
        gameRoundApi.setMovie1(movie1);

        MovieApi movie2 = new MovieApi();
        movie2.setTitle(gameRound.getNameMovie2());
        movie2.setImdbID(gameRound.getIdMovie2());
        gameRoundApi.setMovie2(movie2);

        return gameRoundApi;
    }

    private Optional<GameRound> getOpenRoundByid(String roundIdStr) {
        Long roundId = Long.valueOf(roundIdStr);
        return gameRoundRepository.getOpenRoundByid(roundId);
    }

    @Override
    public GameRound validateRoundParams(GameAwnserApi gameAwnserApi, User user) {
        if(gameAwnserApi.getRoundId() != null && gameAwnserApi.getAwnser() != null) {
            Optional<GameRound> gameRound = getOpenRoundByid(gameAwnserApi.getRoundId());
            if(gameRound.isPresent()) {
                if (gameRound.get().getGame().getUser().getId().equals(user.getId())) {
                    return gameRound.get();
                }else {
                    throw new RuntimeException("Invalid user");
                }
            }else{
                throw new RuntimeException("Invalid roundId");
            }
        }else{
            throw new RuntimeException("Invalid parameters");
        }
    }

    @Override
    public Boolean validateRoundAwnser(GameRound gameRound, GameAwnserApi gameAwnserApi) {
        Boolean isCorrectAwnser = false;
        Game game = gameRound.getGame();
        gameRound.setUserAwnser(gameAwnserApi.getAwnser());

        if (!gameRound.getCorrectAwnser().equals(gameAwnserApi.getAwnser())){
            Integer countError = game.getWrongAnswersCounter() +1;
            game.setWrongAnswersCounter(countError);

            if(countError == 3) {
                game.setEndedAt(LocalDateTime.now());
            }

        } else {
            Integer points = game.getPoints() +1;
            game.setPoints(points);
            isCorrectAwnser = true;
        }
        gameRoundRepository.save(gameRound);

        return isCorrectAwnser;
    }
}
