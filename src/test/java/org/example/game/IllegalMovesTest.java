package org.example.game;

import org.example.model.*;
import org.example.strategy.LowestCardStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IllegalMoveTest {

    @Test
    void playingCardNotInHandThrows() {
        Player a = new Player("A");
        a.addCard(new Card(Suit.CLUBS, Rank.TWO));
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(a, new Player("B"), new Player("C"))));
        g.setStrategy("A", new LowestCardStrategy());
        assertThrows(IllegalMoveException.class,
                () -> g.playCard(a, new Card(Suit.HEARTS, Rank.ACE)));
    }

    @Test
    void playingHeartsAsFirstMoveBeforeBrokenThrows() {
        Player a = new Player("A");
        a.addCard(new Card(Suit.HEARTS, Rank.TWO));
        a.addCard(new Card(Suit.CLUBS, Rank.THREE));
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(a, new Player("B"), new Player("C"))));
        g.setStrategy("A", new LowestCardStrategy());
        assertThrows(IllegalMoveException.class,
                () -> g.playCard(a, new Card(Suit.HEARTS, Rank.TWO)));
    }
}