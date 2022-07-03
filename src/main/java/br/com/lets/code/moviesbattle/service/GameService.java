package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;

import java.util.Map;

public interface GameService {

    public GameRoundDto playGame();
    public Boolean awnserRound(Map<String, Object> awnser);
    public Boolean quitGame();

}
