package org.example.game;

import org.example.model.GameEvent;
import org.example.model.Player;
import org.example.strategy.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты воспроизводимости: одинаковый seed даёт одинаковые события.
 */
class DeterminismTest {

    private List<GameEvent> runOnce(long seed) {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        HeartsGame g = new HeartsGame(seed, new ArrayList<>(ps));
        Random r = new Random(seed);
        g.setStrategy("A", new LowestCardStrategy());
        g.setStrategy("B", new RandomStrategy(r));
        g.setStrategy("C", new LowestCardStrategy());
        g.setStrategy("D", new RandomStrategy(r));
        g.playGame();
        return g.getEvents();
    }

    @Test
    void sameSeedGivesSameEvents() {
        List<GameEvent> a = runOnce(100L);
        List<GameEvent> b = runOnce(100L);
        assertEquals(a.stream().map(GameEvent::getMessage).toList(),
                b.stream().map(GameEvent::getMessage).toList());
    }

    @Test
    void differentSeedsGiveDifferentGames() {
        List<GameEvent> a = runOnce(100L);
        List<GameEvent> b = runOnce(200L);
        assertNotEquals(a.stream().map(GameEvent::getMessage).toList(),
                b.stream().map(GameEvent::getMessage).toList());
    }
}