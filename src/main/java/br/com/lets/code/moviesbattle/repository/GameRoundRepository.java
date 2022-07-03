package br.com.lets.code.moviesbattle.repository;

import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameRoundRepository extends JpaRepository<GameRound, String> {

    @Query("SELECT gr FROM GameRound gr WHERE gr.userAwnser IS NULL AND gr.game = :game")
    GameRound getCurrentRoundByGame(Game game);

    @Query("SELECT gr FROM GameRound gr WHERE gr.userAwnser IS NULL and gr.roundId = :roundId")
    Optional<GameRound> getOpenRoundByid(Long roundId);

    @Query(
            "SELECT gr FROM GameRound gr WHERE gr.game= :game AND ( " +
            "gr.idMovie1= :imdbID1 AND gr.idMovie2=:imdbID2 " +
            "OR " +
            "gr.idMovie2=:imdbID1 AND gr.idMovie1=:imdbID2)"
    )
    Optional<GameRound> hasCurrentMoviesGameCombination(Game game, String imdbID1, String imdbID2);
}
