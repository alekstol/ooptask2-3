package org.example.model;

import java.util.Objects;

/**
 * Событие партии. Механика складывает их в список, вывод — читает.
 * Так механика не знает ничего про консоль.
 */
public final class GameEvent {

    public enum Type { DEAL, PASS, PLAY, TRICK_END, ROUND_END, GAME_END }

    private final Type type;
    private final String message;

    public GameEvent(Type type, String message) {
        this.type = Objects.requireNonNull(type);
        this.message = Objects.requireNonNull(message);
    }

    public Type getType() { return type; }
    public String getMessage() { return message; }

    @Override
    public String toString() { return "[" + type + "] " + message; }
}
