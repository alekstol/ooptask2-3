package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * Игрок: имя, рука, накопленные штрафные очки, очки за текущий раунд.
 * Рука доступна только через методы, чтобы нельзя было «случайно» её испортить.
 */
public class Player {

    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private int totalScore = 0;
    private int roundScore = 0;

    public Player(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не может быть пустым");
        this.name = name;
    }

    public String getName() { return name; }

    /** Копия руки — чтобы внешний код не мог её менять. */
    public List<Card> getHand() {
        List<Card> copy = new ArrayList<>(hand);
        copy.sort(Comparator.naturalOrder());
        return copy;
    }

    public int getTotalScore() { return totalScore; }
    public int getRoundScore() { return roundScore; }

    public void addCard(Card c) { hand.add(c); }
    public void removeCard(Card c) { hand.remove(c); }
    public int handSize() { return hand.size(); }
    public boolean hasCard(Card c) { return hand.contains(c); }

    public void addRoundScore(int p) { roundScore += p; }
    public void commitRoundScore() { totalScore += roundScore; roundScore = 0; }
    public void setRoundScore(int p) { roundScore = p; }
    public void resetTotalScore() { totalScore = 0; roundScore = 0; }

    /** Есть ли в руке карта указанной масти. */
    public boolean hasSuit(Suit s) {
        return hand.stream().anyMatch(c -> c.getSuit() == s);
    }

    /** Все ли карты в руке — черви. */
    public boolean hasOnlyHearts() {
        return !hand.isEmpty() && hand.stream().allMatch(c -> c.getSuit() == Suit.HEARTS);
    }

    @Override
    public String toString() { return name; }
}
