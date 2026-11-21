package org.example.strategy;
import org.example.model.Card;
import org.example.model.GameState;
import java.util.Comparator;
import java.util.List;
/** Выбирает самую старшую карту. */
public class HighestCardStrategy implements Strategy {
    @Override
    public Card chooseCard(List<Card> legalMoves, GameState state) {
        return legalMoves.stream().max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("Нет ходов"));
    }
}