package me.bubbarob19.pongrating.controller;

import jakarta.validation.Valid;
import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.dto.MatchInputDTO;
import me.bubbarob19.pongrating.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/processMatch")
    public ResponseEntity<List<Player>> processMatch(@Valid @RequestBody MatchInputDTO matchInputDTO) {
        return ResponseEntity.ok(matchService.processMatch(matchInputDTO));
    }
}
