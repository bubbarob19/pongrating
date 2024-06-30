package me.bubbarob19.pongrating.service;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.model.LeaderboardEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class LeaderboardService {

    private final PlayerService playerService;

    public List<LeaderboardEntry> getLeaderboard() {
        return playerService.getAllPlayers().stream()
                .sorted(Comparator.comparingInt(player -> player.getDisplayElo() * -1))
                .map(player -> LeaderboardEntry.builder()
                        .id(player.getId())
                        .name(player.getFirstName() + " " + player.getLastName())
                        .elo(player.getDisplayElo())
                        .rank(player.getRank())
                        .matchesPlayed(player.getMatchHistory().size())
                        .winLossRatio((double) player.getWins() / player.getLosses())
                        .build())
                .toList();
    }
}
