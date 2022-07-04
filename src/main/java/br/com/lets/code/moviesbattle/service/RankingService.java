package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.RankingUser;

import java.util.List;

public interface RankingService {

    List<RankingUser> getRanking(String maxResult);
}
