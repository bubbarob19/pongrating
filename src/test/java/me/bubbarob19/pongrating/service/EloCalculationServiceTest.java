package me.bubbarob19.pongrating.service;

import me.bubbarob19.pongrating.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EloCalculationServiceTest {

    @Autowired
    EloCalculationService eloCalculationService;

//    @Test
//    public void calculateDeltaForMatch() {
//        int p1Rating = 1400;
//        int p2Rating = 1800;
//        int p1score = 21;
//        int p2score = 19;
//
//        Player player1 = new Player("id1", "name1", "name1", p1Rating, 0, 0, 0, null, new ArrayList<>());
//        Player player2 = new Player("id1", "name1", "name1", p2Rating, 0, 0, 0, null, new ArrayList<>());
//
//        int expectedDelta = 204;
//
//        assertThat(eloCalculationService.calculateRatingChange(player1, player2, p1score, p2score)).isEqualTo(expectedDelta);
//        assertThat(eloCalculationService.calculateRatingChange(player2, player1, p2score, p1score)).isEqualTo(-expectedDelta);
//    }
}
