package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;
import br.com.lets.code.moviesbattle.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game/")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "play")
    public ResponseEntity<GameRoundDto> playGame() {
        GameRoundDto gameRound = gameService.playGame();
        return ResponseEntity.ok().body(gameRound);
    }

    @PostMapping(value = "awnser")
    public ResponseEntity<String> awnserGame(@RequestBody Map<String, Object> awnser) {
        try {
            boolean correctAwnser = gameService.awnserRound(awnser);
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

    @GetMapping(value = "rank")
    public ResponseEntity getRanks() {
        //TODO
        return ResponseEntity.accepted().build();
    }
}
