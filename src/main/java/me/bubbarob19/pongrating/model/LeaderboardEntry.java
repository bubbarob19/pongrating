package me.bubbarob19.pongrating.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderboardEntry {

    private String id;
    private String name;
    private int elo;
    private Rank rank;
    private int matchesPlayed;
    private double winLossRatio;
}
