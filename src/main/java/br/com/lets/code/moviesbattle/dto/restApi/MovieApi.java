package br.com.lets.code.moviesbattle.dto.restApi;

import java.io.Serializable;

public class MovieApi implements Serializable {

    private String title;

    private String imdbID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

}
