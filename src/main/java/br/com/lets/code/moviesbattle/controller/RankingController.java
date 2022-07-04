package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.RankingUser;
import br.com.lets.code.moviesbattle.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking/")
public class RankingController {

    private RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @Operation(summary = "Get ranking of all players.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get ranking response",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @GetMapping
    public ResponseEntity<List<RankingUser>> getRanking(@RequestParam(defaultValue = "10", required = false) String maxResult) {
        List<RankingUser> rankingUsers = rankingService.getRanking(maxResult);
        return ResponseEntity.ok(rankingUsers);
    }

}
