package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.exception.EntityNotFoundException;
import me.bubbarob19.pongrating.model.Match;
import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.dto.MatchInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private final PlayerService playerService;
    private final EloCalculationService eloCalculationService;
    private final RankCalculationService rankCalculationService;

    @Autowired
    public MatchService(PlayerService playerService, EloCalculationService eloCalculationService, RankCalculationService rankCalculationService) {
        this.playerService = playerService;
        this.eloCalculationService = eloCalculationService;
        this.rankCalculationService = rankCalculationService;
    }

    public List<Player> processMatch(MatchInputDTO matchInputDTO) {
        Player winner = playerService.getPlayerById(matchInputDTO.getWinnerId())
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + matchInputDTO.getWinnerId() + " not found"));
        Player loser = playerService.getPlayerById(matchInputDTO.getLoserId())
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + matchInputDTO.getLoserId() + " not found"));

        updatePlayerData(winner, loser, matchInputDTO);

        return List.of(winner, loser);
    }

    private void updatePlayerData(Player winner, Player loser, MatchInputDTO matchData) {
        int winnerNewRating = eloCalculationService.calculateNewRating(
                winner.getElo(),
                loser.getElo(),
                matchData.getWinnerScore(),
                matchData.getLoserScore()
        );

        int loserNewRating = eloCalculationService.calculateNewRating(
                loser.getElo(),
                winner.getElo(),
                matchData.getLoserScore(),
                matchData.getWinnerScore()
        );

        Match match = new Match(
                matchData.getDate(),
                matchData.getWinnerId(),
                matchData.getLoserId(),
                matchData.getWinnerScore(),
                matchData.getLoserScore(),
                winnerNewRating - winner.getElo(),
                loserNewRating - loser.getElo()
        );

        winner.getMatchHistory().add(match);
        loser.getMatchHistory().add(match);

        winner.setElo(winnerNewRating);
        loser.setElo(loserNewRating);

        winner.setDisplayElo(eloCalculationService.calculateDisplayElo(winnerNewRating, winner.getMatchHistory().size()));
        loser.setDisplayElo(eloCalculationService.calculateDisplayElo(loserNewRating, loser.getMatchHistory().size()));

        winner.setRank(rankCalculationService.calculateRank(winner.getDisplayElo()));
        loser.setRank(rankCalculationService.calculateRank(loser.getDisplayElo()));

        playerService.updatePlayer(winner.getId(), winner);
        playerService.updatePlayer(loser.getId(), loser);
    }
}
