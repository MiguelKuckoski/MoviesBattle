package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.repository.RankingRepository;
import br.com.lets.code.moviesbattle.service.RankingService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    private RankingRepository rankingRepository;

    public RankingServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public List<RankingUser> getRanking(String maxResult) {
        Integer maxResultInt = Integer.parseInt(maxResult);
        List<Object[]> rankingUsers = rankingRepository.getRanking(maxResultInt);
        return getRankingListFromObject(rankingUsers);
    }

    private List<RankingUser> getRankingListFromObject(List<Object[]> objectList) {
        return objectList.stream()
                .map(object -> {
                    BigInteger userId = (BigInteger) object[0];
                    String username = (String) object[1];
                    BigInteger hitsPercent = (BigInteger) object[2];
                    return new RankingUser(userId.longValue() , username, hitsPercent == null ? 0 : hitsPercent.intValue());
                })
                .toList();
    }
}
