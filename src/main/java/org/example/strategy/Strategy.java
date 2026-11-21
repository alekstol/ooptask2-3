package org.example.strategy;
import org.example.model.Card;
import org.example.model.GameState;

import java.util.List;
/**
 * Паттерн «Стратегия»: правило выбора карты из набора допустимых ходов.
 * Реализации не знают о правилах — они получают уже отфильтрованный список.
 */
public interface Strategy {
    /**
     * @param legalMoves гарантированно непустой список допустимых карт
     * @param state снимок состояния (для контекста, может не использоваться)
     * @return карта из {@code legalMoves}
     */
    Card chooseCard(List<Card> legalMoves, GameState state);
}