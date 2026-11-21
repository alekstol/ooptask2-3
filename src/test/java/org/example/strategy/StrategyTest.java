package org.example.strategy;

import org.example.model.Card;
import org.example.model.Rank;
import org.example.model.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты паттерна «Стратегия»: каждая реализация возвращает карту из допустимых.
 */
class StrategyTest {

    private final List<Card> legal = List.of(
            new Card(Suit.CLUBS, Rank.TWO),
            new Card(Suit.CLUBS, Rank.FIVE),
            new Card(Suit.CLUBS, Rank.NINE));

    @Test
    void lowestStrategyReturnsMinimum() {
        Card chosen = new LowestCardStrategy().chooseCard(legal, null);
        assertEquals(new Card(Suit.CLUBS, Rank.TWO), chosen);
    }

    @Test
    void highestStrategyReturnsMaximum() {
        Card chosen = new HighestCardStrategy().chooseCard(legal, null);
        assertEquals(new Card(Suit.CLUBS, Rank.NINE), chosen);
    }

    @Test
    void randomStrategyReturnsOneOfLegal() {
        RandomStrategy s = new RandomStrategy(new Random(1));
        Card chosen = s.chooseCard(legal, null);
        assertTrue(legal.contains(chosen));
    }
}