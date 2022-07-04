package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.Movie;
import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;
import br.com.lets.code.moviesbattle.dto.restApi.MovieApi;
import br.com.lets.code.moviesbattle.model.User;
import br.com.lets.code.moviesbattle.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private GameController gameController;

    @MockBean
    private GameService gameService;

    private GameRoundApi gameRoundApi;
    private GameAwnserApi gameAwnserApi;

    @BeforeEach
    public void setup() {
        MovieApi movie = new MovieApi();
        movie.setImdbID("123");
        movie.setTitle("teste");

        MovieApi movie1 = new MovieApi();
        movie1.setImdbID("456");
        movie1.setTitle("teste2");

        gameRoundApi = new GameRoundApi();
        gameRoundApi.setRoundId(1L);
        gameRoundApi.setMovie1(movie);
        gameRoundApi.setMovie2(movie1);
        Mockito.when(gameService.playGame()).thenReturn(gameRoundApi);

        gameAwnserApi = new GameAwnserApi();
        gameAwnserApi.setAwnser("correct");
        gameAwnserApi.setRoundId("1");
        Mockito.when(gameService.awnserRound(gameAwnserApi)).thenReturn(true);

        Mockito.when(gameService.quitGame()).thenReturn(true);
    }

    @Test
    void validatePlayGame() {
        ResponseEntity<GameRoundApi> response = gameController.playGame();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1L, response.getBody().getRoundId());
        Assertions.assertEquals("123", response.getBody().getMovie1().getImdbID());
        Assertions.assertEquals("456", response.getBody().getMovie2().getImdbID());
        Assertions.assertEquals("teste", response.getBody().getMovie1().getTitle());
        Assertions.assertEquals("teste2", response.getBody().getMovie2().getTitle());

    }

    @Test
    void validateAwnserGame() {
        ResponseEntity<String> response = gameController.awnserGame(gameAwnserApi);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Correct awnser, +1 point. Request play to a new round", response.getBody());
    }

    @Test
    void validateQuitGame() {
        ResponseEntity<String> response = gameController.quitGame();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Game ended successful. Request play to a new game", response.getBody());
    }
}
