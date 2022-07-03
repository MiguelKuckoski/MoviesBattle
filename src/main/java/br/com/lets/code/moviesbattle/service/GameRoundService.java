package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;

import java.util.Optional;

public interface GameRoundService {
    Optional<GameRound> getCurrentRound(Game game);

    GameRound getNewGameRound(Game game);

    GameRoundApi gameRoundToDto(GameRound gameRound);

    GameRound validateRoundParams(GameAwnserApi gameAwnserApi, User user);

    Boolean validateRoundAwnser(GameRound gameRound, GameAwnserApi gameAwnserApi);
}
