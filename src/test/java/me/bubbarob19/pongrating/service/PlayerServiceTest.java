package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Player;
import me.bubbarob19.pongrating.model.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PlayerServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PlayerService playerService;

    @BeforeEach
    public void clearDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void fillBlankPlayerAttributes() {
        Player player = new Player("id1", "name1", "name1", 0, 0, 0, 0, null, new ArrayList<>());
        playerService.addPlayer(player);

        assertThat(player.getElo()).isEqualTo(1400);
        assertThat(player.getDisplayElo()).isEqualTo(0);
        assertThat(player.getRank()).isEqualTo(Rank.NEWBIE);
    }
}
