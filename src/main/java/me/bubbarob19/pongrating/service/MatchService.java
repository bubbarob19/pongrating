package me.bubbarob19.pongrating.service;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.exception.EntityNotFoundException;
import me.bubbarob19.pongrating.model.Match;
import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.dto.MatchInputDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final PlayerService playerService;
    private final EloCalculationService eloCalculationService;
    private final RankCalculationService rankCalculationService;

    public List<Player> processMatch(MatchInputDTO matchInputDTO) {
        Player winner = playerService.getPlayerById(matchInputDTO.getWinnerId())
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + matchInputDTO.getWinnerId() + " not found"));
        Player loser = playerService.getPlayerById(matchInputDTO.getLoserId())
                .orElseThrow(() -> new EntityNotFoundException("Player with ID " + matchInputDTO.getLoserId() + " not found"));

        updatePlayerData(winner, loser, matchInputDTO);

        return List.of(winner, loser);
    }

    private void updatePlayerData(Player winner, Player loser, MatchInputDTO matchData) {
        int originalWinnerDisplayElo = winner.getDisplayElo();
        int originalLoserDisplayElo = loser.getDisplayElo();

        int winnerDelta = eloCalculationService.calculateRatingChange(
                winner,
                loser,
                matchData.getWinnerScore(),
                matchData.getLoserScore()
        );

        int loserDelta = eloCalculationService.calculateRatingChange(
                loser,
                winner,
                matchData.getLoserScore(),
                matchData.getWinnerScore()
        );

        winner.setWins(winner.getWins() + 1);
        loser.setLosses(loser.getLosses() + 1);

        winner.setElo(winner.getElo() + winnerDelta);
        loser.setElo(loser.getElo() + loserDelta);

        winner.setDisplayElo(eloCalculationService.calculateDisplayElo(winner.getElo(), winner.getMatchHistory().size() + 1));
        loser.setDisplayElo(eloCalculationService.calculateDisplayElo(loser.getElo(), loser.getMatchHistory().size() + 1));

        winner.setRank(rankCalculationService.calculateRank(winner.getDisplayElo()));
        loser.setRank(rankCalculationService.calculateRank(loser.getDisplayElo()));

        Match match = Match.builder()
                .date(matchData.getDate() != null ? matchData.getDate() : new Date())
                .winnerId(matchData.getWinnerId())
                .loserId(matchData.getLoserId())
                .winnerName(winner.getFirstName() + " " + winner.getLastName())
                .loserName(loser.getFirstName() + " " + loser.getLastName())
                .winnerScore(matchData.getWinnerScore())
                .loserScore(matchData.getLoserScore())
                .winnerEloChange(winner.getDisplayElo() - originalWinnerDisplayElo)
                .loserEloChange(loser.getDisplayElo() - originalLoserDisplayElo)
                .winnerNewDisplayElo(winner.getDisplayElo())
                .loserNewDisplayElo(loser.getDisplayElo())
                .build();

        winner.getMatchHistory().add(match);
        loser.getMatchHistory().add(match);

        playerService.updatePlayer(winner.getId(), winner);
        playerService.updatePlayer(loser.getId(), loser);
    }
}
