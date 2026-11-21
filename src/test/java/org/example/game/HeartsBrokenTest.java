package org.example.game;

import org.example.model.*;
import org.example.strategy.LowestCardStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты флага «пробой»: после хода червой heartsBroken становится true.
 */
class HeartsBrokenTest {

    @Test
    void playingHeartBreaksHearts() {
        Player a = new Player("A");
        a.addCard(new Card(Suit.HEARTS, Rank.TWO));
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(a, new Player("B"), new Player("C"))));
        g.setStrategy("A", new LowestCardStrategy());
        // только черви на руке — можно ходить, и это пробивает
        g.playCard(a, new Card(Suit.HEARTS, Rank.TWO));
        assertTrue(g.snapshot().isHeartsBroken());
    }

    @Test
    void playingNonHeartDoesNotBreakHearts() {
        Player a = new Player("A");
        a.addCard(new Card(Suit.CLUBS, Rank.TWO));
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(a, new Player("B"), new Player("C"))));
        g.setStrategy("A", new LowestCardStrategy());
        g.playCard(a, new Card(Suit.CLUBS, Rank.TWO));
        assertFalse(g.snapshot().isHeartsBroken());
    }
}