package br.com.lets.code.moviesbattle.repository.impl;

import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.repository.RankingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class RankingRepositoryImpl implements RankingRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> getRanking(Integer maxResult){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT TB_USER.ID, TB_USER.USERNAME, ((GAME.POINTS *100) / count(GAME_ROUND .GAME_ID) ) AS PERCENT_HITS ");
        stringBuilder.append("FROM GAME ");
        stringBuilder.append("JOIN GAME_ROUND ON GAME_ROUND.GAME_ID = GAME.ID AND GAME_ROUND.USER_AWNSER IS NOT NULL ");
        stringBuilder.append("JOIN TB_USER ON TB_USER .ID = GAME .USER_ID ");
        stringBuilder.append("GROUP BY TB_USER.ID ");
        stringBuilder.append("ORDER BY PERCENT_HITS DESC ");
        stringBuilder.append("LIMIT :limit ");

        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        query.setParameter("limit", maxResult);

        List<Object[]> rankingUsers = query.getResultList();

        return rankingUsers;
    }
}
