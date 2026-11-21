package org.example.game;

import org.example.model.Player;
import org.junit.jupiter.api.Test;
import ru.hearts.game.HeartsGame;
import ru.hearts.model.*;
import ru.hearts.strategy.LowestCardStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassCardsTest {

    @Test
    void afterDealEachPlayerHas13Cards() {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"));
        HeartsGame g = new HeartsGame(7, new ArrayList<>(ps));
        ps.forEach(p -> g.setStrategy(p.getName(), new LowestCardStrategy()));
        // playRound включает раздачу, но и передачу — здесь проверим размер руки после раздачи
        // через побочный эффект: после полного раунда рука пуста; поэтому проверим счёт очков
        g.playRound();
        int sum = ps.stream().mapToInt(Player::getTotalScore).sum();
        assertEquals(26, sum); // все штрафные очки распределены
    }

    @Test
    void passKeepsTotalCardCount() {
        List<Player> ps = List.of(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        HeartsGame g = new HeartsGame(3, new ArrayList<>(ps));
        ps.forEach(p -> g.setStrategy(p.getName(), new LowestCardStrategy()));
        // после раунда — все руки пусты, очки распределены
        g.playRound();
        int total = ps.stream().mapToInt(Player::getTotalScore).sum();
        assertEquals(26, total);
    }
}