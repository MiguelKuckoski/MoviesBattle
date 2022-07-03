package br.com.lets.code.moviesbattle.repository;

import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String> {

    @Query("SELECT g FROM Game g where g.endedAt IS NULL AND g.user = :user")
    Optional<Game> getCurrentGameByUser(User user);
}
