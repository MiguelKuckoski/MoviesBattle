package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;
import br.com.lets.code.moviesbattle.repository.GameRepository;
import br.com.lets.code.moviesbattle.repository.GameRoundRepository;
import br.com.lets.code.moviesbattle.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class GameServiceTest  {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRoundRepository gameRoundRepository;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("teste");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        GameRound gameRound = new GameRound();
        gameRound.setRoundId(1L);
        gameRound.setCorrectAwnser("correct");
        Game game = new Game();
        game.setId(1L);
        game.setUser(user);
        gameRound.setGame(game);
        Mockito.when(gameRoundRepository.getOpenRoundByid(1L))
                .thenReturn(Optional.ofNullable(gameRound));

        Mockito.when(userDetailsService.loadUserByUsername(user.getUsername()))
                .thenReturn(user);
    }

    @Test
    void shouldReturnGameRound() {
        GameRoundApi gameRound = gameService.playGame();
        Assertions.assertNotNull(gameRound.getMovie1());
        Assertions.assertNotNull(gameRound.getMovie2());
        Assertions.assertNotEquals(gameRound.getMovie1().getImdbID(), gameRound.getMovie2().getImdbID());
    }

    @Test
    void shouldReturnFalseAwnser() {
        GameAwnserApi gameAwnserApi = new GameAwnserApi();
        gameAwnserApi.setAwnser("incorrect");
        gameAwnserApi.setRoundId("1");
        boolean awnser = gameService.awnserRound(gameAwnserApi);

        Assertions.assertEquals(false, awnser);
    }

    @Test
    void shouldReturnTrueAwnser() {
        GameAwnserApi gameAwnserApi = new GameAwnserApi();
        gameAwnserApi.setAwnser("correct");
        gameAwnserApi.setRoundId("1");
        boolean awnser = gameService.awnserRound(gameAwnserApi);

        Assertions.assertEquals(true, awnser);
    }

    @Test
    void shouldQuitGame(){
        Mockito.when(gameRepository.getCurrentGameByUser(user))
                .thenReturn(Optional.ofNullable(new Game()));

        boolean gameEnded = gameService.quitGame();
        Assertions.assertEquals(true, gameEnded);
    }

    @Test
    void noPendingToQuit() {
        Mockito.when(gameRepository.getCurrentGameByUser(user))
                .thenReturn(Optional.empty());

        boolean gameEnded = gameService.quitGame();
        Assertions.assertEquals(false, gameEnded);
    }

}
