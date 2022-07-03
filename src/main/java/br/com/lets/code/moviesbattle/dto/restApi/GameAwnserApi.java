package br.com.lets.code.moviesbattle.dto.restApi;

import java.io.Serializable;

public class GameAwnserApi implements Serializable {

    private String roundId;
    private String awnser;

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public String getAwnser() {
        return awnser;
    }

    public void setAwnser(String awnser) {
        this.awnser = awnser;
    }
}
