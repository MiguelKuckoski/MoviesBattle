package br.com.lets.code.moviesbattle.dto;

import java.io.Serializable;

public class GameRoundDto implements Serializable {

    private Long roundId;
    private Movie movie1;
    private Movie movie2;

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Movie getMovie1() {
        return movie1;
    }

    public void setMovie1(Movie movie1) {
        this.movie1 = movie1;
    }

    public Movie getMovie2() {
        return movie2;
    }

    public void setMovie2(Movie Movie) {
        this.movie2 = movie2;
    }
}
