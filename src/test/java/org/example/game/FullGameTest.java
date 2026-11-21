package org.example.game;

import org.example.model.Player;
import org.example.strategy.HighestCardStrategy;
import org.example.strategy.LowestCardStrategy;
import org.example.strategy.RandomStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FullGameTest {

    @Test
    void fullGameWithFixedSeedTerminatesAndProducesEvents() {
        long seed = 42L;
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        HeartsGame g = new HeartsGame(seed, new ArrayList<>(ps));
        Random r = new Random(seed);
        g.setStrategy("A", new LowestCardStrategy());
        g.setStrategy("B", new RandomStrategy(r));
        g.setStrategy("C", new HighestCardStrategy());
        g.setStrategy("D", new LowestCardStrategy());

        g.playGame();
        assertTrue(g.isGameOver(100));
        assertFalse(g.getEvents().isEmpty());
    }
}