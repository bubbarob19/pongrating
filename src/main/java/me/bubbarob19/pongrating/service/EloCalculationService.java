package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Player;
import org.springframework.stereotype.Service;

@Service
public class EloCalculationService {
    private static final int BASE_K_FACTOR = 64;
    private static final int WINNING_BONUS = 30;

    public int calculateRatingChange(Player player, Player opponent, int playerScore, int opponentScore) {
        double expectedScorePlayer = calculateExpectedScore(player.getElo(), opponent.getElo());

        if (playerScore > opponentScore)
            playerScore += WINNING_BONUS;
        else
            opponentScore += WINNING_BONUS;

        double playerActualScore = (double) playerScore / (playerScore + opponentScore);

        int kFactor = BASE_K_FACTOR * calculateKFactorMultiplier(player);
        int playerRating = player.getElo();
        int playerNewRating = (int) Math.round(playerRating + kFactor * (playerActualScore - expectedScorePlayer));

        return playerNewRating - playerRating;
    }

    public int calculateDisplayElo(int actualElo, int matchesPlayed) {
        if (matchesPlayed == 5)
            return actualElo - 50;
        else if (matchesPlayed == 4)
            return actualElo - 150;
        else if (matchesPlayed == 3)
            return actualElo - 300;
        else if (matchesPlayed == 2)
            return actualElo - 550;
        else if (matchesPlayed == 1)
            return actualElo - 900;
        else if (matchesPlayed == 0)
            return actualElo - 1400;

        return actualElo;
    }

    private double calculateExpectedScore(int player1Rating, int player2Rating) {
        return 1.0 / (1 + Math.pow(10, (player2Rating - player1Rating) / 400.0));
    }

    private int calculateKFactorMultiplier(Player player) {
        int matchesPlayed = player.getMatchHistory().size();
        if (matchesPlayed < 2)
            return 5;
        else if (matchesPlayed < 5)
            return 3;
        else if (matchesPlayed < 10)
            return 2;
        return 1;
    }
}
