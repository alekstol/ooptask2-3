package org.example.game;

import java.util.Map;

/** Итог одного раунда: очки за раунд по каждому игроку. */
public record RoundResult(Map<String, Integer> roundScores, boolean shootTheMoon) {}