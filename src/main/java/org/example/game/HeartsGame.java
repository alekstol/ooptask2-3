package org.example.game;
import org.example.model.*;
import org.example.strategy.Strategy;

import java.util.*;
/**
 * Механика игры «Червы». Не пишет в консоль — только накапливает {@link GameEvent}.
 * Все случайные величины берутся из одного {@link Random} с зерном,
 * поэтому при одинаковом зерне партия воспроизводима.
 */
public class HeartsGame {

    private static final int MAX_SCORE_DEFAULT = 100;
    private static final int PASS_COUNT = 3;

    private final Random random;
    private final List<Player> players;
    private final Map<String, Strategy> strategies = new LinkedHashMap<>();
    private final List<GameEvent> events = new ArrayList<>();

    private int roundNumber = 0;
    private List<Card> currentTrick = new ArrayList<>();
    private Suit currentSuit = null;
    private boolean heartsBroken = false;
    private int trickLeader = 0;
    private int lastTrickWinner = -1;

    public HeartsGame(long seed, List<Player> players) {
        if (players == null || players.size() < 3)
            throw new IllegalArgumentException("Нужно минимум 3 игрока");
        this.random = new Random(seed);
        this.players = new ArrayList<>(players);
        this.trickLeader = 0;
    }

    /** Назначает стратегию игроку по имени. */
    public void setStrategy(String playerName, Strategy s) {
        strategies.put(playerName, Objects.requireNonNull(s));
    }

    public List<GameEvent> getEvents() { return Collections.unmodifiableList(events); }

    /** Снимок текущего состояния. */
    public GameState snapshot() {
        return new GameState(players, roundNumber, currentTrick,
                currentSuit, heartsBroken, trickLeader);
    }

    /** Играет партию до {@code maxScore} очков у любого игрока. */
    public void playGame(int maxScore) {
        players.forEach(Player::resetTotalScore);
        roundNumber = 0;

        while (!isGameOver(maxScore)) {
            playRound();
            roundNumber++;
        }
        events.add(new GameEvent(GameEvent.Type.GAME_END,
                "Игра окончена. Итог: " + scoreSummary()));
    }

    /** Играет партию до 100 очков (по умолчанию). */
    public void playGame() { playGame(MAX_SCORE_DEFAULT); }

    public boolean isGameOver(int maxScore) {
        return players.stream().anyMatch(p -> p.getTotalScore() >= maxScore);
    }

    private String scoreSummary() {
        StringBuilder sb = new StringBuilder();
        for (Player p : players) sb.append(p.getName()).append("=").append(p.getTotalScore()).append(" ");
        return sb.toString().trim();
    }

    /** Один раунд: раздача, передача, 13 взяток, подсчёт. */
    public RoundResult playRound() {
        dealCards();
        passCards();
        for (int i = 0; i < 13; i++) playTrick();
        return endRound();
    }

    private void dealCards() {
        List<Card> deck = new ArrayList<>(52);
        for (Suit s : Suit.values())
            for (Rank r : Rank.values())
                deck.add(new Card(s, r));
        Collections.shuffle(deck, random);

        players.forEach(p -> p.getHand().forEach(p::removeCard)); // очистка
        for (Player p : players) {
            for (Card c : p.getHand()) p.removeCard(c);
        }

        int idx = 0;
        for (Card c : deck) {
            players.get(idx % players.size()).addCard(c);
            idx++;
        }

        events.add(new GameEvent(GameEvent.Type.DEAL, "Раздача: " + players.size() + " игроков"));
    }

    /** Передаёт по 3 карты: направление чередуется каждый раунд (лево, право, через одного). */
    void passCards() {
        int dir = roundNumber % 3;
        if (dir == 2) {
            dir = 0;
        }
        List<List<Card>> toPass = new ArrayList<>();
        for (Player p : players) {
            List<Card> hand = p.getHand();
            List<Card> chosen = hand.stream()
                    .sorted(Comparator.naturalOrder())
                    .limit(PASS_COUNT)
                    .toList();
            toPass.add(new ArrayList<>(chosen));
        }
        for (int i = 0; i < players.size(); i++)
            for (Card c : toPass.get(i)) players.get(i).removeCard(c);
        int n = players.size();
        for (int i = 0; i < n; i++) {
            int target = (dir == 0) ? (i + 1) % n : (i - 1 + n) % n;
            for (Card c : toPass.get(i)) players.get(target).addCard(c);
        }
        events.add(new GameEvent(GameEvent.Type.PASS,
                "Передача " + (dir == 0 ? "влево" : "вправо")));
    }
    /** Одна взятка: по очереди все кладут карту. */
    void playTrick() {
        currentTrick.clear();
        currentSuit = null;
        for (int step = 0; step < players.size(); step++) {
            int idx = (trickLeader + step) % players.size();
            Player p = players.get(idx);
            List<Card> legal = legalMoves(p);
            if (legal.isEmpty())
                throw new IllegalStateException("Нет допустимых ходов у " + p.getName());
            Strategy s = strategies.get(p.getName());
            if (s == null) throw new IllegalStateException("Нет стратегии у " + p.getName());

            Card chosen = s.chooseCard(legal, snapshot());
            playCard(p, chosen);
        }
        Card best = currentTrick.get(0);
        int bestIdx = 0;
        for (int i = 1; i < currentTrick.size(); i++) {
            Card c = currentTrick.get(i);
            if (c.getSuit() == currentSuit && c.compareTo(best) > 0
                    && (best.getSuit() != currentSuit || c.getSuit() == currentSuit)) {
                best = c;
                bestIdx = i;
            }
        }
        bestIdx = 0;
        for (int i = 1; i < currentTrick.size(); i++) {
            Card c = currentTrick.get(i);
            if (c.getSuit() == currentSuit &&
                    (currentTrick.get(bestIdx).getSuit() != currentSuit ||
                            c.compareTo(currentTrick.get(bestIdx)) > 0)) {
                bestIdx = i;
            }
        }
        int winner = (trickLeader + bestIdx) % players.size();
        lastTrickWinner = winner;

        int points = currentTrick.stream().mapToInt(Card::penaltyPoints).sum();
        if (points > 0) {
            players.get(winner).addRoundScore(points);
            events.add(new GameEvent(GameEvent.Type.TRICK_END,
                    "Взятку взял " + players.get(winner).getName() + ", +" + points));
        } else {
            events.add(new GameEvent(GameEvent.Type.TRICK_END,
                    "Взятку взял " + players.get(winner).getName() + ", без очков"));
        }

        trickLeader = winner;
    }
    /**
     * Допустимые ходы для игрока.
     * <ul>
     *   <li>Первый ход взятки: любую, кроме червей, если пробой не случился и есть другие масти.</li>
     *   <li>Не первый ход: если есть масть хода — только её; иначе — любую, но черви только при пробое.</li>
     * </ul>
     */
    public List<Card> legalMoves(Player p) {
        List<Card> hand = p.getHand();
        if (hand.isEmpty()) return List.of();
        if (currentSuit == null) {
            List<Card> noHearts = hand.stream()
                    .filter(c -> c.getSuit() != Suit.HEARTS)
                    .toList();
            if (!heartsBroken && !noHearts.isEmpty()) return noHearts;
            return hand;
        }
        List<Card> inSuit = hand.stream()
                .filter(c -> c.getSuit() == currentSuit)
                .toList();
        if (!inSuit.isEmpty()) return inSuit;

        List<Card> noHearts = hand.stream()
                .filter(c -> c.getSuit() != Suit.HEARTS)
                .toList();
        if (!heartsBroken && !noHearts.isEmpty()) return noHearts;
        return hand;
    }
    /** Кладёт карту. Бросает {@link IllegalMoveException}, если ход недопустим. */
    public void playCard(Player p, Card c) {
        if (!p.hasCard(c))
            throw new IllegalMoveException("Карты " + c + " нет в руке игрока " + p.getName());
        if (!legalMoves(p).contains(c))
            throw new IllegalMoveException("Недопустимый ход: " + c + " (масть хода " + currentSuit + ")");
        p.removeCard(c);
        currentTrick.add(c);
        if (currentSuit == null) currentSuit = c.getSuit();
        if (c.getSuit() == Suit.HEARTS) heartsBroken = true;

        events.add(new GameEvent(GameEvent.Type.PLAY, p.getName() + " → " + c));
    }
    RoundResult endRound() {
        // «Забрать всё»: если один игрок собрал все 26 — он получает 0, остальные +26
        int totalPenalty = players.stream().mapToInt(Player::getRoundScore).sum();
        boolean shootMoon = totalPenalty == 26 &&
                players.stream().anyMatch(p -> p.getRoundScore() == 26);

        Map<String, Integer> scores = new LinkedHashMap<>();
        for (Player p : players) {
            if (shootMoon && p.getRoundScore() == 26) {
                p.setRoundScore(0);
            } else if (shootMoon) {
                p.setRoundScore(26);
            }
            scores.put(p.getName(), p.getRoundScore());
            p.commitRoundScore();
        }
        events.add(new GameEvent(GameEvent.Type.ROUND_END,
                "Раунд " + roundNumber + ": " + scores + (shootMoon ? " (забрал всё!)" : "")));
        currentTrick.clear();
        currentSuit = null;
        heartsBroken = false;

        return new RoundResult(scores, shootMoon);
    }
}