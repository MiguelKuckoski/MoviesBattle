package br.com.lets.code.moviesbattle.service.impl;

import br.com.lets.code.moviesbattle.dto.GameRoundDto;
import br.com.lets.code.moviesbattle.dto.Movie;
import br.com.lets.code.moviesbattle.model.Game;
import br.com.lets.code.moviesbattle.model.GameRound;
import br.com.lets.code.moviesbattle.model.User;
import br.com.lets.code.moviesbattle.repository.GameRoundRepository;
import br.com.lets.code.moviesbattle.service.GameRoundService;

import java.time.LocalDateTime;
import java.util.Optional;

public class GameRoundServiceImpl implements GameRoundService {

    private GameRoundRepository gameRoundRepository;

    @Override
    public Optional<GameRound> getCurrentRound(Game game) {
        GameRound gameRound = gameRoundRepository.getCurrentRoundByGame(game);
        return Optional.of(gameRound);
    }

    @Override
    public GameRoundDto getNewGameRound() {
        return new GameRoundDto(); //TODO
    }

    @Override
    public void saveNewGameRound(GameRoundDto gameRoundDto, Game game) {
        GameRound gameRound = new GameRound();
        gameRound.setGame(game);
//        gameRound.setIdMovie1(gameRoundDto.getMovie1().getImdbID());
//        gameRound.setIdMovie2(gameRoundDto.getMovie2().getImdbID());

//        String correctAwnser = getBiggerRating(gameRoundDto.getMovie1(), gameRoundDto.getMovie2());
//        gameRound.setCorrectAwnser(correctAwnser);

        gameRoundRepository.save(gameRound);
    }

    private String getBiggerRating(Movie movie1, Movie movie2) {
        Double rattingMovie1 = movie1.getImdbVotes() * movie1.getImdbRating();
        Double rattingMovie2 = movie2.getImdbVotes() * movie2.getImdbRating();

        if(rattingMovie1 > rattingMovie2) {
            return movie1.getImdbID();
        } else {
            return movie2.getImdbID();
        }
    }

    @Override
    public GameRoundDto gameRoundToDto(GameRound gameRound) {
        GameRoundDto gameRoundDto = new GameRoundDto();
        gameRoundDto.setRoundId(gameRound.getRoundId());

        Movie movie1 = new Movie();
        movie1.setTitle(gameRound.getNameMovie1());
        movie1.setImdbID(gameRound.getIdMovie1());
        gameRoundDto.setMovie1(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle(gameRound.getNameMovie2());
        movie2.setImdbID(gameRound.getIdMovie2());
        gameRoundDto.setMovie2(movie2);

        return gameRoundDto;
    }

    private Optional<GameRound> getOpenRoundByid(String roundId) {
        return gameRoundRepository.getOpenRoundByid(roundId);
    }

    @Override
    public GameRound validateRoundParams(String roundId, User user, String awnser) {
        if(roundId != null && awnser != null) {
            Optional<GameRound> gameRound = getOpenRoundByid(roundId);
            if(gameRound.isPresent()) {
                if (gameRound.get().getGame().getUser().getId().equals(user.getId())) {
                    return gameRound.get();
                }else {
                    throw new RuntimeException("Invalid user");
                }
            }else{
                throw new RuntimeException("Invalid roundId");
            }
        }else{
            throw new RuntimeException("Invalid parameters");
        }
    }

    @Override
    public Boolean validateRoundAwnser(GameRound gameRound, String awnser) {
        Boolean isCorrectAwnser = false;
        Game game = gameRound.getGame();
        gameRound.setUserAwnser(awnser);

        if (!gameRound.getCorrectAwnser().equals(awnser)){
            Integer countError = game.getWrongAnswersCounter() +1;
            game.setWrongAnswersCounter(countError);

            if(countError == 3) {
                game.setEndedAt(LocalDateTime.now());
            }

        } else {
            Integer points = game.getPoints() +1;
            game.setPoints(points);
            isCorrectAwnser = true;
        }
        gameRoundRepository.save(gameRound);

        return isCorrectAwnser;
    }
}
