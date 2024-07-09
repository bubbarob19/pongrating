package me.bubbarob19.pongrating.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    private Date date;
    private String winnerId;
    private String loserId;
    private String winnerName;
    private String loserName;
    private int winnerScore;
    private int loserScore;
    private int winnerEloChange;
    private int loserEloChange;
    private int winnerNewDisplayElo;
    private int loserNewDisplayElo;
}
