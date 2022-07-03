package br.com.lets.code.moviesbattle.repository;

import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameRoundRepository extends JpaRepository<GameRound, String> {

    @Query("SELECT gr FROM GameRound gr WHERE gr.userAwnser IS NULL AND gr.game = :game")
    GameRound getCurrentRoundByGame(Game game);

    @Query("SELECT gr FROM GameRound gr WHERE gr.userAwnser IS NULL and gr.roundId = :roundId")
    Optional<GameRound> getOpenRoundByid(String roundId);
}
