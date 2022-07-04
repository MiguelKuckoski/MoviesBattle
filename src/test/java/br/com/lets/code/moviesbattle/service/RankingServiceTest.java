package br.com.lets.code.moviesbattle.service;

import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.repository.GameRepository;
import br.com.lets.code.moviesbattle.repository.RankingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class RankingServiceTest {

    @Autowired
    private RankingService rankingService;

    @MockBean
    private RankingRepository rankingRepository;

    @BeforeEach
    public void setup() {
        Object[] obj = new Object[3];
        obj[0] = new BigInteger("1");
        obj[1] = "teste";
        obj[2] = new BigInteger("100");

        Object[] obj1 = new Object[3];
        obj1[0] = new BigInteger("2");
        obj1[1] = "teste2";
        obj1[2] = new BigInteger("50");

        Object[] obj2 = new Object[3];
        obj2[0] = new BigInteger("3");
        obj2[1] = "teste3";
        obj2[2] = new BigInteger("20");


        Mockito.when(rankingRepository.getRanking(10)).thenReturn(Arrays.asList(obj, obj1, obj2));
    }

    @Test
    void shouldReturnRankingList() {
        List<RankingUser> rankingUsers = rankingService.getRanking("10");
        Assertions.assertNotNull(rankingUsers);
        Assertions.assertEquals(3, rankingUsers.size());
        Assertions.assertEquals(1, rankingUsers.get(0).getIdUser());
        Assertions.assertEquals(2, rankingUsers.get(1).getIdUser());
        Assertions.assertEquals(3, rankingUsers.get(2).getIdUser());

        Assertions.assertEquals(100, rankingUsers.get(0).getPercentHits());
        Assertions.assertEquals(50, rankingUsers.get(1).getPercentHits());
        Assertions.assertEquals(20, rankingUsers.get(2).getPercentHits());
    }


}
