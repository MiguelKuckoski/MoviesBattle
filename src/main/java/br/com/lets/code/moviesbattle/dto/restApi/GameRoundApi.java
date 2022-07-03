package br.com.lets.code.moviesbattle.dto.restApi;

import java.io.Serializable;

public class GameRoundApi implements Serializable {

    private Long roundId;
    private MovieApi movie1;
    private MovieApi movie2;

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public MovieApi getMovie1() {
        return movie1;
    }

    public void setMovie1(MovieApi movie1) {
        this.movie1 = movie1;
    }

    public MovieApi getMovie2() {
        return movie2;
    }

    public void setMovie2(MovieApi movie2) {
        this.movie2 = movie2;
    }
}
