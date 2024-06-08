package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Rank;
import org.springframework.stereotype.Service;

@Service
public class RankCalculationService {
    public Rank calculateRank(int displayElo) {
        if (displayElo >= 2500)
            return Rank.GRANDMASTER;
        else if (displayElo >= 2100)
            return Rank.MASTER;
        else if (displayElo >= 1800)
            return Rank.CANDIDATE_MASTER;
        else if (displayElo >= 1600)
            return Rank.EXPERT;
        else if (displayElo >= 1400)
            return Rank.SPECIALIST;
        else if (displayElo >= 1100)
            return Rank.PUPIL;
        else
            return Rank.NEWBIE;
    }
}
