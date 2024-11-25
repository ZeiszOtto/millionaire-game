/** @file GameModel.java */ 
package millionaire.model;

import java.util.*;
import java.time.Duration;


/**
 * @brief A játék logikáját és állapotát kezelő osztály.
 * 
 * Ez az osztály felelős a játék fő működéséért, beleértve:
 * - Kérdések kiválasztását
 * - Válaszok feldolgozását
 * - Nyeremények kezelését
 * - Játékállapot követését
 */
public class GameModel {

    /** @brief Nyereményszintek összegei csökkenő sorrendben */
    private static final long[] PRIZES = {
        40_000_000L, 20_000_000L, 10_000_000L, 5_000_000L, 3_000_000L,
        1_500_000L, 800_000L, 500_000L, 300_000L, 200_000L,
        100_000L, 50_000L, 25_000L, 10_000L, 5_000L
    };
    
    /** @brief Garantált nyereménnyel járó szintek */
    private static final int[] SAFE_SPOTS = {5, 8, 11, 13, 15};
    
    private final QuestionRepository questionRepository;    /**< Kérdések tárolója */
    private final PlayerModel player;                       /**< Játékos modell */
    private Question currentQuestion;                       /**< Aktuális kérdés */
    private final Random random;                            /**< Véletlenszám generátor */
    private boolean isGameOver;                             /**< Játék vége jelző */
    private final Duration timeLimit;                       /**< Idő limit, ha akarunk beállítani */
    

    /**
    * @brief Konstruktor a játékmodell létrehozásához
    * @param questionRepository A kérdéseket tároló adatbázis
    * @param player Az aktuális játékos modellje
    */
    public GameModel(QuestionRepository questionRepository, PlayerModel player) {
        this.questionRepository = questionRepository;
        this.player = player;
        this.random = new Random();
        this.isGameOver = false;
        this.timeLimit = null;  // Duration.ofMinutes(X) 
        this.currentQuestion = selectNextQuestion();
    }
    

    /**
     * @brief Kiválaszt egy következő kérdést az aktuális szintnek megfelelően
     * @return A kiválasztott kérdés
     * @throws IllegalStateException Ha nincs elérhető kérdés az adott szinten
     */
    public Question selectNextQuestion() {
        List<Question> questions = questionRepository.getQuestionsByDifficulty(player.getCurrentLevel());
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available for level " + player.getCurrentLevel());
        }
        currentQuestion = questions.get(random.nextInt(questions.size()));
        return currentQuestion;
    }
    

    /**
     * @brief Feldolgozza a játékos válaszát
     * @param selectedAnswer A játékos által választott válasz betűjele
     * @return A játék állapota a válasz feldolgozása után
     */
    public GameState processAnswer(String selectedAnswer) {
        if (isGameOver || currentQuestion == null) {
            return GameState.LOST;
        }
        
        boolean isCorrect = currentQuestion.getCorrectAnswer().equals(selectedAnswer);
        
        if (!isCorrect) {
            isGameOver = true;
            return GameState.LOST;
        }
        
        if (player.getCurrentLevel() == 15) {
            isGameOver = true;
            return GameState.WON;
        }
        
        player.incrementLevel();
        currentQuestion = selectNextQuestion();
        return GameState.CORRECT;
    }
    

    /**
     * @brief Meghatározza az utolsó elért garantált nyereményt
     * @return Az utolsó garantált nyeremény összege
     */
    public long getLastSafePrize() {
        for (int i = SAFE_SPOTS.length - 1; i >= 0; i--) {
            if (player.getCurrentLevel() >= SAFE_SPOTS[i]) {
                return PRIZES[15 - SAFE_SPOTS[i]];
            }
        }
        return 0L;
    }
    

    /**
     * @brief Visszaadja az aktuális szinthez tartozó nyereményösszeget
     * @return Az aktuális nyereményösszeg
     */
    public long getCurrentPrize() {
        return PRIZES[15 - player.getCurrentLevel()];
    }
    

    /** @brief Visszaadja az aktuális játékost */
    public PlayerModel getPlayer() { return player; }


    /** @brief Visszaadja az aktuális kérdést */
    public Question getCurrentQuestion() { return currentQuestion; }


    /** @brief Visszaadja, hogy véget ért-e a játék */
    public boolean isGameOver() { return isGameOver; }


    /**
     * @brief Létrehozza a nyereményszintek listáját
     * @return Nyereményszintek listája a megfelelő információkkal
     */
    public static List<PrizeLevel> getPrizeLevels() {
        List<PrizeLevel> levels = new ArrayList<>();
        for (int i = 0; i < PRIZES.length; i++) {
            int level = 15 - i;
            long prize = PRIZES[i];
            boolean isSafeSpot = Arrays.stream(SAFE_SPOTS).anyMatch(spot -> spot == level);
            levels.add(new PrizeLevel(level, prize, isSafeSpot));
        }
        return levels;
    }


    /**
     * @brief Frissíti a játékos játékidejét
     * @param elapsedTime Az eltelt játékidő
     */
    public void updatePlayTime(Duration elapsedTime) {
        player.setPlaytime(elapsedTime);
    }


    /**
     * @brief Ellenőrzi, hogy a játék időlimitje lejárt-e (ha van)
     * @param currentTime Az aktuális játékidő
     * @return true ha lejárt az idő, false ha nem
     */
    public boolean isTimeUp(Duration currentTime) {
        return timeLimit != null && currentTime.compareTo(timeLimit) > 0;
    }
}