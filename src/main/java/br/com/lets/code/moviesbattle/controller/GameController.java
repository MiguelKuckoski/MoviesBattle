package br.com.lets.code.moviesbattle.controller;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;
import br.com.lets.code.moviesbattle.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/game/")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = "play")
    public ResponseEntity<GameRoundDto> playGame() {
        GameRoundDto gameRound = gameService.playGame();
        return ResponseEntity.ok().body(gameRound);
    }

    @PostMapping(value = "awnser")
    public ResponseEntity<String> awnserGame(@RequestBody Map<String, Object> awnser) {
        Boolean correctAwnser = gameService.awnserRound(awnser);
        String response = "Request play to a new round";
        if(correctAwnser) {
            response = "Correct awnser, +1 point. " + response;
        } else {
            response = "Wrong awnser. " + response;
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "quit")
    public ResponseEntity<GameRoundDto> quitGame() {
        GameRoundDto gameRound = gameService.playGame();
        return ResponseEntity.ok().body(gameRound);
    }
}
