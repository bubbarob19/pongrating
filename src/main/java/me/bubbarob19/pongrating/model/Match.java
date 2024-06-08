package me.bubbarob19.pongrating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    private Date date;
    private String winnerId;
    private String loserId;
    private int winnerScore;
    private int loserScore;
    private int winnerEloChange;
    private int loserEloChange;
}
