package br.com.lets.code.moviesbattle.repository;

import br.com.lets.code.moviesbattle.dto.RankingUser;

import java.util.List;


public interface RankingRepository {

    List<Object[]> getRanking(Integer maxResult);
}
