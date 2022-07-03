package br.com.lets.code.moviesbattle.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
    private Integer points;
    private Integer wrongAnswersCounter;

    @OneToMany(mappedBy = "game")
    private List<GameRound> rounds;

    public Game() {
        this.startedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getWrongAnswersCounter() {
        return wrongAnswersCounter;
    }

    public void setWrongAnswersCounter(Integer wrongAnswersCounter) {
        this.wrongAnswersCounter = wrongAnswersCounter;
    }

    public List<GameRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<GameRound> rounds) {
        this.rounds = rounds;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
}
