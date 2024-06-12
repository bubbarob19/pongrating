package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.Rank;
import me.bubbarob19.pongrating.model.dto.MatchInputDTO;
import me.bubbarob19.pongrating.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MatchServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @BeforeEach
    public void clearDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void simulatePlayerStartingOut() {
        Player player1 = new Player("id1", "name1", "name1", 1400, 0, Rank.NEWBIE, new ArrayList<>());
        Player player2 = new Player("id2", "name2", "name2", 1400, 0, Rank.NEWBIE, new ArrayList<>());
        playerService.addPlayer(player1);
        playerService.addPlayer(player2);

        for (int i = 0; i < 5; i++) {
            var list = matchService.processMatch(createSampleMatch());
            player1 = list.get(0);
            player2 = list.get(1);

            int totalElo = player1.getElo() + player2.getElo();
            assertThat(player1.getDisplayElo() + player2.getDisplayElo()).isLessThan(totalElo);
            assertThat(player1.getDisplayElo() + player2.getDisplayElo()).isGreaterThan(0);
        }

        var list = matchService.processMatch(createSampleMatch());
        player1 = list.get(0);
        player2 = list.get(1);

        int totalElo = player1.getElo() + player2.getElo();
        assertThat(player1.getDisplayElo() + player2.getDisplayElo()).isEqualTo(totalElo);
    }

    private MatchInputDTO createSampleMatch() {
        int winningScore = 21;
        int losingScore = new Random().nextInt(1, 21);
        return new MatchInputDTO(new Date(), "id1", "id2", winningScore, losingScore);
    }
}
