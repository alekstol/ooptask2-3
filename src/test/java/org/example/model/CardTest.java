package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void penaltyForHeartIsOne() {
        assertEquals(1, new Card(Suit.HEARTS, Rank.TWO).penaltyPoints());
        assertEquals(1, new Card(Suit.HEARTS, Rank.ACE).penaltyPoints());
    }

    @Test
    void penaltyForQueenOfSpadesIsThirteen() {
        assertEquals(13, new Card(Suit.SPADES, Rank.QUEEN).penaltyPoints());
    }

    @Test
    void noPenaltyForOtherCards() {
        assertEquals(0, new Card(Suit.CLUBS, Rank.ACE).penaltyPoints());
        assertEquals(0, new Card(Suit.SPADES, Rank.KING).penaltyPoints());
    }

    @Test
    void sortingUsesSuitThenRank() {
        List<Card> hand = List.of(
                new Card(Suit.HEARTS, Rank.TWO),
                new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.TWO));
        List<Card> sorted = hand.stream().sorted().toList();
        assertEquals(new Card(Suit.CLUBS, Rank.TWO), sorted.get(0));
        assertEquals(new Card(Suit.CLUBS, Rank.ACE), sorted.get(1));
        assertEquals(new Card(Suit.HEARTS, Rank.TWO), sorted.get(2));
    }

    @Test
    void equalsAndHashCode() {
        assertEquals(new Card(Suit.HEARTS, Rank.KING), new Card(Suit.HEARTS, Rank.KING));
        assertEquals(new Card(Suit.HEARTS, Rank.KING).hashCode(),
                new Card(Suit.HEARTS, Rank.KING).hashCode());
    }
}