package org.example.game;

import org.example.model.Player;
import org.junit.jupiter.api.Test;
import ru.hearts.game.HeartsGame;
import ru.hearts.model.*;
import ru.hearts.strategy.LowestCardStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundScoringTest {

    @Test
    void roundDistributesExactly26Points() {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"));
        HeartsGame g = new HeartsGame(11, new ArrayList<>(ps));
        ps.forEach(p -> g.setStrategy(p.getName(), new LowestCardStrategy()));
        g.playRound();
        assertEquals(26, ps.stream().mapToInt(Player::getTotalScore).sum());
    }
}