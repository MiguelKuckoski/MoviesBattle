package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.restApi.GameAwnserApi;
import br.com.lets.code.moviesbattle.dto.restApi.GameRoundApi;
import br.com.lets.code.moviesbattle.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game/")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Get a new or current game round.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Round info.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = GameRoundApi.class)),
            })
    })
    @GetMapping(value = "play")
    public ResponseEntity<GameRoundApi> playGame() {
        GameRoundApi gameRound = gameService.playGame();
        return ResponseEntity.ok().body(gameRound);
    }

    @Operation(summary = "Awnser a game round.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports the result of the round",
                    content = { @Content(mediaType = "Text",
                            schema = @Schema(implementation = String.class)) })
    })
    @PostMapping(value = "awnser")
    public ResponseEntity<String> awnserGame(@RequestBody GameAwnserApi gameAwnser) {
        try {
            boolean correctAwnser = gameService.awnserRound(gameAwnser);
            String response = "Request play to a new round";
            if(correctAwnser) {
                response = "Correct awnser, +1 point. " + response;
            } else {
                response = "Wrong awnser. " + response;
            }
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @Operation(summary = "Quit the current game if exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports the status of request",
                    content = { @Content(mediaType = "Text",
                            schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(value = "quit")
    public ResponseEntity<String> quitGame() {
        boolean gameEnded = gameService.quitGame();
        String response = "Request play to a new game";
        if(gameEnded){
            response = "Game ended successful. " + response;
        }else {
            response = "No pending game to end. " + response;
        }
        return ResponseEntity.ok().body(response);
    }

}
