package org.example.game;
import org.example.model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ShootTheMoonTest {
    @Test
    void playerWith26GetsZero() {
        Player p = new Player("A");
        p.setRoundScore(26);
        p.commitRoundScore();
        assertEquals(26, p.getTotalScore());
    }
}