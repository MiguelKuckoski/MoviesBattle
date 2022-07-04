package br.com.lets.code.moviesbattle.dto;

import java.io.Serializable;

public class RankingUser implements Serializable {

    private Long idUser;
    private String username;
    private Integer percentHits;

    public RankingUser(Long idUser, String username, Integer percentHits) {
        this.idUser = idUser;
        this.username = username;
        this.percentHits = percentHits;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPercentHits() {
        if(percentHits == null) {
            percentHits = 0;
        }
        return percentHits;
    }

    public void setPercentHits(Integer percentHits) {
        this.percentHits = percentHits;
    }
}
