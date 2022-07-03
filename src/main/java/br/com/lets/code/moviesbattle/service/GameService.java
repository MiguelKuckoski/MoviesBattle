package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;

import java.util.Map;

public interface GameService {

    public GameRoundApi playGame();
    public Boolean awnserRound(GameAwnserApi gameAwnserApi);
    public Boolean quitGame();

}
