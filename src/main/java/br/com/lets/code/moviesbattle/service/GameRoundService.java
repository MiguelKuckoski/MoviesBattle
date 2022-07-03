package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;

import java.util.Optional;

public interface GameRoundService {
    Optional<GameRound> getCurrentRound(Game game);

    GameRound getNewGameRound(Game game);

    GameRoundDto gameRoundToDto(GameRound gameRound);

    GameRound validateRoundParams(String roundId, User user, String awnser);

    Boolean validateRoundAwnser(GameRound gameRound, String awnser);
}
