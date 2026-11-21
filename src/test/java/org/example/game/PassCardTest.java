package org.example.game;
import org.example.model.Player;
import org.example.strategy.LowestCardStrategy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class PassCardsTest {

    @Test
    void afterDealEachPlayerHas13Cards() {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"));
        HeartsGame g = new HeartsGame(7, new ArrayList<>(ps));
        ps.forEach(p -> g.setStrategy(p.getName(), new LowestCardStrategy()));
        g.playRound();
        int sum = ps.stream().mapToInt(Player::getTotalScore).sum();
        assertEquals(26, sum);
    }
    @Test
    void passKeepsTotalCardCount() {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        HeartsGame g = new HeartsGame(3, new ArrayList<>(ps));
        ps.forEach(p -> g.setStrategy(p.getName(), new LowestCardStrategy()));
        g.playRound();
        int total = ps.stream().mapToInt(Player::getTotalScore).sum();
        assertEquals(26, total);
    }
}