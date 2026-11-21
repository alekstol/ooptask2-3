package org.example.model;

/**
 * Кидается при попытке сделать недопустимый ход.
 * Отдельный тип, чтобы тесты ловили именно его, а не общий {@link IllegalArgumentException}.
 */
public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String message) { super(message); }
}