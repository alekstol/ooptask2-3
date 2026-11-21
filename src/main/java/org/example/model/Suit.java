package org.example.model;
/**
 * Масть карты. Порядок объявления задаёт порядок для сортировки рук.
 */
public enum Suit {
    CLUBS("♣"), DIAMONDS("♦"), SPADES("♠"), HEARTS("♥");

    private final String symbol;

    Suit(String symbol) { this.symbol = symbol; }

    /** Символ масти для вывода. */
    public String getSymbol() { return symbol; }
}