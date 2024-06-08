package me.bubbarob19.pongrating.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchInputDTO {

    private Date date;
    private String winnerId;
    private String loserId;
    private int winnerScore;
    private int loserScore;
}
