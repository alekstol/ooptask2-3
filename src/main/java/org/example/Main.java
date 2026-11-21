package org.example;
import org.example.game.HeartsGame;
import org.example.model.Player;
import org.example.strategy.HighestCardStrategy;
import org.example.strategy.LowestCardStrategy;
import org.example.strategy.RandomStrategy;
import org.example.view.ConsoleView;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        long seed = 2024L;
        List<Player> players = List.of(
                new Player("Аня"), new Player("Боря"), new Player("Вера"), new Player("Гоша"));
        HeartsGame game = new HeartsGame(seed, new ArrayList<>(players));
        Random r = new Random(seed);
        game.setStrategy("Аня", new LowestCardStrategy());
        game.setStrategy("Боря", new RandomStrategy(r));
        game.setStrategy("Вера", new HighestCardStrategy());
        game.setStrategy("Гоша", new LowestCardStrategy());
        game.playGame();
        ConsoleView view = new ConsoleView();
        game.getEvents().forEach(view::printEvent);
        view.printState(game.snapshot());
    }
}