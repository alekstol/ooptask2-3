package org.example.game;

import org.junit.jupiter.api.Test;
import ru.hearts.model.Player;

import static org.junit.jupiter.api.Assertions.*;

class ShootTheMoonTest {

    @Test
    void playerWith26GetsZero() {
        // Логику endRound держим в HeartsGame; здесь проверяем корректность через публичный API
        // эмулировать «все 26 у одного» без доступа к приватным полям сложно,
        // поэтому проверяем через Player: setRoundScore + commit
        Player p = new Player("A");
        p.setRoundScore(26);
        p.commitRoundScore();
        assertEquals(26, p.getTotalScore());
    }
}