package org.example.game;

import org.example.model.Card;
import org.example.model.Player;
import org.example.model.Rank;
import org.example.model.Suit;
import org.example.strategy.LowestCardStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LegalMovesTest {

    private HeartsGame gameWith(Player... ps) {
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(ps)));
        for (Player p : ps) g.setStrategy(p.getName(), new LowestCardStrategy());
        return g;
    }

    @Test
    void firstMoveCannotBeHeartsBeforeBroken() {
        Player p = new Player("A");
        p.addCard(new Card(Suit.HEARTS, Rank.TWO));
        p.addCard(new Card(Suit.CLUBS, Rank.THREE));
        HeartsGame g = gameWith(p, new Player("B"), new Player("C"));
        List<Card> legal = g.legalMoves(p);
        assertEquals(1, legal.size());
        assertEquals(Suit.CLUBS, legal.get(0).getSuit());
    }

    @Test
    void firstMoveWithOnlyHeartsAllowed() {
        Player p = new Player("A");
        p.addCard(new Card(Suit.HEARTS, Rank.TWO));
        HeartsGame g = gameWith(p, new Player("B"), new Player("C"));
        assertEquals(1, g.legalMoves(p).size());
    }
    @Test
    void mustFollowSuitWhenPossible() {
        Player a = new Player("A");
        a.addCard(new Card(Suit.CLUBS, Rank.TWO));
        a.addCard(new Card(Suit.HEARTS, Rank.THREE));
        Player b = new Player("B");
        b.addCard(new Card(Suit.CLUBS, Rank.FOUR));
        b.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));
        HeartsGame g = new HeartsGame(1, new ArrayList<>(List.of(a, b, new Player("C"))));
        g.setStrategy("A", new LowestCardStrategy());
        g.setStrategy("B", new LowestCardStrategy());
        g.setStrategy("C", new LowestCardStrategy());
        List<Card> legal = g.legalMoves(b);
        assertTrue(legal.stream().allMatch(c -> c.getSuit() != Suit.HEARTS));
    }
    @Test
    void noSuitMeansAnyButHeartsUntilBroken() {
        Player p = new Player("A");
        p.addCard(new Card(Suit.HEARTS, Rank.TWO));
        p.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        HeartsGame g = gameWith(p, new Player("B"), new Player("C"));
        assertTrue(g.legalMoves(p).stream().noneMatch(c -> c.getSuit() == Suit.HEARTS));
    }
}