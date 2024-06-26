package me.bubbarob19.pongrating.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "player")
public class Player {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int elo;
    private int displayElo;
    private int wins;
    private int losses;
    private Rank rank;
    private List<Match> matchHistory = new ArrayList<>();
}
