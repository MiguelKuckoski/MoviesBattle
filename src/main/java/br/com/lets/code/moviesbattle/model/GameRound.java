package br.com.lets.code.moviesbattle.model;

import javax.persistence.*;

@Entity
public class GameRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roundId;
    private String idMovie1;
    private String nameMovie1;
    private String idMovie2;
    private String nameMovie2;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    private String userAwnser;
    private String correctAwnser;
    public String getIdMovie1() {
        return idMovie1;
    }
    public String getNameMovie1() {
        return nameMovie1;
    }

    public void setNameMovie1(String nameMovie1) {
        this.nameMovie1 = nameMovie1;
    }

    public String getNameMovie2() {
        return nameMovie2;
    }

    public void setNameMovie2(String nameMovie2) {
        this.nameMovie2 = nameMovie2;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public String getIdMOovie1() {
        return idMovie1;
    }

    public void setIdMovie1(String idMOovie1) {
        this.idMovie1 = idMOovie1;
    }

    public String getIdMovie2() {
        return idMovie2;
    }

    public void setIdMovie2(String idMOovie2) {
        this.idMovie2 = idMOovie2;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUserAwnser() {
        return userAwnser;
    }

    public void setUserAwnser(String userAwnser) {
        this.userAwnser = userAwnser;
    }

    public String getCorrectAwnser() {
        return correctAwnser;
    }

    public void setCorrectAwnser(String correctAwnser) {
        this.correctAwnser = correctAwnser;
    }
}
