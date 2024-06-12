package me.bubbarob19.pongrating.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EloCalculationServiceTest {

    @Autowired
    EloCalculationService eloCalculationService;

    @Test
    public void calculateDeltaForMatch() {
        int p1Rating = 1400;
        int p2Rating = 1800;
        int p1score = 21;
        int p2score = 19;

        int expectedDelta = 20;

        assertThat(eloCalculationService.calculateRatingChange(p1Rating, p2Rating, p1score, p2score)).isEqualTo(expectedDelta);
        assertThat(eloCalculationService.calculateRatingChange(p2Rating, p1Rating, p2score, p1score)).isEqualTo(-expectedDelta);
    }
}
