package me.bubbarob19.pongrating.controller;

import jakarta.validation.Valid;
import me.bubbarob19.pongrating.model.LeaderboardEntry;
import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.dto.MatchInputDTO;
import me.bubbarob19.pongrating.service.LeaderboardService;
import me.bubbarob19.pongrating.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboardList() {
        return ResponseEntity.ok(leaderboardService.getLeaderboard());
    }
}
