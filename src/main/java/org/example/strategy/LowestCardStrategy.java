package org.example.strategy;
import org.example.model.Card;
import org.example.model.GameState;

import java.util.Comparator;
import java.util.List;
/** Выбирает самую младшую карту. */
public class LowestCardStrategy implements Strategy {
    @Override
    public Card chooseCard(List<Card> legalMoves, GameState state) {
        return legalMoves.stream().min(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("Нет ходов"));
    }
}