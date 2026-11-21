package org.example.view;

import org.example.model.GameEvent;
import org.example.model.GameState;
import org.example.model.Player;

/** Слой вывода. Единственное место в проекте, где есть System.out. */
public class ConsoleView {
    public void printEvent(GameEvent e) {
        System.out.println(e.getMessage());
    }
    public void printState(GameState s) {
        System.out.println("=== Раунд " + s.getRoundNumber() + " ===");
        for (Player p : s.getPlayers()) {
            System.out.printf("%-6s рука: %s | всего: %d | раунд: %d%n",
                    p.getName(), p.getHand(), p.getTotalScore(), p.getRoundScore());
        }
        System.out.println("Масть хода: " + s.getCurrentSuit() + ", пробой: " + s.isHeartsBroken());
    }
}