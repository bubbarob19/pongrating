package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.LeaderboardEntry;
import me.bubbarob19.pongrating.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LeaderboardService {

    private final PlayerService playerService;

    @Autowired
    public LeaderboardService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return playerService.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(player -> player.getElo() * -1))
                .map(player -> LeaderboardEntry.builder()
                        .id(player.getId())
                        .name(player.getFirstName() + " " + player.getLastName())
                        .elo(player.getElo())
                        .rank(player.getRank())
                        .matchesPlayed(player.getMatchHistory().size())
                        .winLossRatio((double) player.getWins() / player.getLosses())
                        .build())
                .toList();
    }
}
