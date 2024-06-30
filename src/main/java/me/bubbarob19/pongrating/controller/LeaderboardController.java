package me.bubbarob19.pongrating.controller;

import lombok.AllArgsConstructor;
import me.bubbarob19.pongrating.model.LeaderboardEntry;
import me.bubbarob19.pongrating.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboards")
@AllArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboardList() {
        return ResponseEntity.ok(leaderboardService.getLeaderboard());
    }
}
