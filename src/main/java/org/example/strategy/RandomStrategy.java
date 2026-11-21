package org.example.strategy;

import org.example.model.Card;
import org.example.model.GameState;

import java.util.List;
import java.util.Random;

/** Случайный ход из допустимых. Генератор передаётся извне — для воспроизводимости. */
public class RandomStrategy implements Strategy {

    private final Random random;

    public RandomStrategy(Random random) { this.random = random; }

    @Override
    public Card chooseCard(List<Card> legalMoves, GameState state) {
        return legalMoves.get(random.nextInt(legalMoves.size()));
    }
}