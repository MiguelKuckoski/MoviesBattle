package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.repository.GameRoundRepository;
import br.com.lets.code.moviesbattle.service.RankingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class RankingControllerTest {


    @Autowired
    private RankingController rankingController;

    @MockBean
    private RankingService rankingService;

    @BeforeEach
    public void setup() {
        RankingUser rankingUser = new RankingUser();
        rankingUser.setIdUser(1L);
        rankingUser.setUsername("teste");
        rankingUser.setPercentHits(100);

        RankingUser rankingUser2 = new RankingUser();
        rankingUser2.setIdUser(2l);
        rankingUser2.setUsername("teste2");
        rankingUser2.setPercentHits(50);

        RankingUser rankingUser3 = new RankingUser();
        rankingUser3.setIdUser(3l);
        rankingUser3.setUsername("teste3");
        rankingUser3.setPercentHits(20);

        Mockito.when(rankingService.getRanking("10")).thenReturn(Arrays.asList(rankingUser, rankingUser2, rankingUser3));
    }

    @Test
    void ShouldReturnRankingList() {
        ResponseEntity<List<RankingUser>> response = rankingController.getRanking("10");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(3, response.getBody().size());

        Assertions.assertEquals("teste", response.getBody().get(0).getUsername());
        Assertions.assertEquals("teste2", response.getBody().get(1).getUsername());
        Assertions.assertEquals("teste3", response.getBody().get(2).getUsername());
    }
}
