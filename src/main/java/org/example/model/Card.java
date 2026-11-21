package org.example.model;

import java.util.Objects;
/**
 * Игральная карта. Неизменяема.
 * Реализует {@link Comparable}: сначала масть, потом достоинство.
 */
public final class Card implements Comparable<Card> {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = Objects.requireNonNull(suit);
        this.rank = Objects.requireNonNull(rank);
    }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }

    /**
     * Штрафные очки за карту: черви — 1, дама пик — 13, остальные — 0.
     */
    public int penaltyPoints() {
        if (suit == Suit.HEARTS) return 1;
        if (suit == Suit.SPADES && rank == Rank.QUEEN) return 13;
        return 0;
    }

    /** Является ли карта штрафной. */
    public boolean isPenalty() {
        return penaltyPoints() > 0;
    }

    @Override
    public int compareTo(Card o) {
        int bySuit = Integer.compare(this.suit.ordinal(), o.suit.ordinal());
        return bySuit != 0 ? bySuit : Integer.compare(this.rank.ordinal(), o.rank.ordinal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card c)) return false;
        return suit == c.suit && rank == c.rank;
    }

    @Override
    public int hashCode() { return Objects.hash(suit, rank); }

    @Override
    public String toString() { return rank + "" + suit.getSymbol(); }
}