package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;
import br.com.lets.code.moviesbattle.repository.GameRepository;
import br.com.lets.code.moviesbattle.service.GameRoundService;
import br.com.lets.code.moviesbattle.service.GameService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    private UserDetailsService userDetailsService;

    private GameRoundService gameRoundService;

    public GameServiceImpl(GameRepository gameRepository, GameRoundService gameRoundService, UserDetailsService userDetailsService) {
        this.gameRepository = gameRepository;
        this.gameRoundService = gameRoundService;
        this.userDetailsService = userDetailsService;

    }

    @Override
    public GameRoundDto playGame() {
        User user = getLoggerUser();
        Game game = getUserGame(user);
        return getGameRound(game);
    }

    private GameRoundDto getGameRound(Game game) {
        GameRoundDto gameRoundDto;

        Optional<GameRound> currentGameRound = gameRoundService.getCurrentRound(game);
        if(currentGameRound.isPresent()) {
            gameRoundDto = gameRoundService.gameRoundToDto(currentGameRound.get());
        } else {
            GameRound gameRound = gameRoundService.getNewGameRound(game);
            gameRoundDto = gameRoundService.gameRoundToDto(gameRound);
        }
        return gameRoundDto;
    }

    private Game getUserGame(User user) {
        Optional<Game> game = gameRepository.getCurrentGameByUser(user);
        if(game.isPresent()){
            return game.get();
        }else{
            Game newGame = new Game();
            newGame.setUser(user);
            gameRepository.save(newGame);
            return newGame;
        }
    }

    @Override
    public Boolean awnserRound(Map<String, Object> playerAwnser) {
        String roundId = (String) playerAwnser.get("roundId");
        String awnser = (String) playerAwnser.get("awnser");
        User user = getLoggerUser();
        GameRound gameRound= gameRoundService.validateRoundParams(roundId, user, awnser);
        return gameRoundService.validateRoundAwnser(gameRound, awnser);
    }

    @Override
    public Boolean quitGame() {
        User user = getLoggerUser();
        Optional<Game> optGame = gameRepository.getCurrentGameByUser(user);
        if(optGame.isPresent()) {
            Game game = optGame.get();
            game.setEndedAt(LocalDateTime.now());
            gameRepository.save(game);
            return true;
        }else{
            return false;
        }
    }

    private User getLoggerUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        return (User) user;
    }


}
