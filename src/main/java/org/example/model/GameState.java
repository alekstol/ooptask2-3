package org.example.model;

import java.util.List;
/**
 * Неизменяемый снимок состояния. Возвращается наружу из {@code HeartsGame}.
 * Механика работает с изменяемыми полями, а наружу отдаёт такой снимок.
 */
public final class GameState {

    private final List<Player> players;      // копия списка; Player неизменяем снаружи по руке
    private final int roundNumber;
    private final List<Card> currentTrick;
    private final Suit currentSuit;
    private final boolean heartsBroken;
    private final int trickLeader;

    public GameState(List<Player> players, int roundNumber, List<Card> currentTrick,
                     Suit currentSuit, boolean heartsBroken, int trickLeader) {
        this.players = List.copyOf(players);
        this.roundNumber = roundNumber;
        this.currentTrick = List.copyOf(currentTrick);
        this.currentSuit = currentSuit;
        this.heartsBroken = heartsBroken;
        this.trickLeader = trickLeader;
    }

    public List<Player> getPlayers() { return players; }
    public int getRoundNumber() { return roundNumber; }
    public List<Card> getCurrentTrick() { return currentTrick; }
    public Suit getCurrentSuit() { return currentSuit; }
    public boolean isHeartsBroken() { return heartsBroken; }
    public int getTrickLeader() { return trickLeader; }

    @Override
    public String toString() {
        return "GameState{round=" + roundNumber + ", trick=" + currentTrick +
                ", suit=" + currentSuit + ", broken=" + heartsBroken + "}";
    }
}
