/** @file GameModelTest.java */
package millionaire.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Teszteléshez használt QuestionRepository implementáció.
 * Előre definiált kérdéseket szolgáltat a tesztek számára.
 */
class TestQuestionRepository extends QuestionRepository {
    private final List<Question> questions = new ArrayList<>();
    
    /**
     * Kérdés hozzáadása a teszt repository-hoz
     */
    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    /**
     * Adott nehézségű kérdések lekérése
     */
    @Override
    public List<Question> getQuestionsByDifficulty(int difficulty) {
        return questions.stream()
                .filter(q -> q.getDifficulty() == difficulty)
                .collect(Collectors.toList());
    }
    
    /**
     * Az összes kérdés lekérése
     */
    @Override
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }
}

/**
 * A GameModel osztály tesztesetei.
 */
public class GameModelTest {
    private GameModel gameModel;
    private PlayerModel player;
    private TestQuestionRepository repository;
    private Question testQuestion;

    /**
     * Teszt előkészítése
     */
    @BeforeEach
    void setUp() {
        repository = new TestQuestionRepository();
        player = new PlayerModel("Teszt Játékos");
        
        // Teszt kérdések létrehozása minden nehézségi szinthez
        for (int i = 1; i <= 15; i++) {
            Question question = new Question.QuestionBuilder()
                .difficulty(i)
                .question(i + ". szintű teszt kérdés?")
                .optionA("A válasz")
                .optionB("B válasz")
                .optionC("C válasz")
                .optionD("D válasz")
                .correctAnswer("B")
                .category("TESZT")
                .build();
                
            repository.addQuestion(question);
        }
        
        gameModel = new GameModel(repository, player);
        testQuestion = repository.getQuestionsByDifficulty(1).get(0);
    }

    /**
     * A játék inicializálásának tesztelése
     */
    @Test
    void testGameInitialization() {
        assertNotNull(gameModel.getCurrentQuestion());
        assertEquals(1, player.getCurrentLevel());
        assertEquals(0L, player.getPrize());
        assertFalse(gameModel.isGameOver());
        assertEquals(1, gameModel.getCurrentQuestion().getDifficulty());
    }

    /**
     * Kérdés kiválasztásának tesztelése
     */
    @Test
    void testQuestionSelection() {
        // Normál eset tesztelése
        Question selectedQuestion = gameModel.selectNextQuestion();
        assertEquals(1, selectedQuestion.getDifficulty());
    }

    /**
     * Helyes válasz feldolgozásának tesztelése
     */
    @Test
    void testCorrectAnswerProcessing() {
        // Helyes válasz feldolgozása
        GameState result = gameModel.processAnswer("B");
        
        assertEquals(GameState.CORRECT, result);
        assertEquals(2, player.getCurrentLevel());
        assertFalse(gameModel.isGameOver());
        assertEquals(2, gameModel.getCurrentQuestion().getDifficulty());
    }

    /**
     * Helytelen válasz feldolgozásának tesztelése
     */
    @Test
    void testIncorrectAnswerProcessing() {
        GameState result = gameModel.processAnswer("A");
        
        assertEquals(GameState.LOST, result);
        assertTrue(gameModel.isGameOver());
        assertEquals(0L, gameModel.getLastSafePrize()); // Első szinten nincs garantált nyeremény
        assertEquals(1, player.getCurrentLevel());
    }

    /**
     * Utolsó kérdés megválaszolásának tesztelése
     */
    @Test
    void testWinningGame() {
        // Játékos beállítása az utolsó szintre
        PlayerModel winningPlayer = new PlayerModel("Győztes Játékos");
        for (int i = 1; i < 15; i++) {
            winningPlayer.incrementLevel();
        }
        
        GameModel winningGame = new GameModel(repository, winningPlayer);
        GameState result = winningGame.processAnswer("B"); // Helyes válasz az utolsó kérdésre
        
        assertEquals(GameState.WON, result);
        assertTrue(winningGame.isGameOver());
        assertEquals(40_000_000L, winningGame.getCurrentPrize());
    }

    /**
     * Garantált nyeremények tesztelése
     */
    @Test
    void testSafePrizes() {
        // 5. szint (100.000 Ft) tesztelése
        for (int i = 1; i < 5; i++) {
            player.incrementLevel();
        }
        assertEquals(100_000L, gameModel.getLastSafePrize());
        
        // 8. szint (500.000 Ft) tesztelése
        for (int i = 5; i < 8; i++) {
            player.incrementLevel();
        }
        assertEquals(500_000L, gameModel.getLastSafePrize());
        
        // 11. szint (3.000.000 Ft) tesztelése
        for (int i = 8; i < 11; i++) {
            player.incrementLevel();
        }
        assertEquals(3_000_000L, gameModel.getLastSafePrize());
    }

    /**
     * Nyereménylétra tesztelése
     */
    @Test
    void testPrizeLevels() {
        List<PrizeLevel> levels = GameModel.getPrizeLevels();
        
        assertEquals(15, levels.size());
        
        // Főnyeremény ellenőrzése
        PrizeLevel topPrize = levels.get(0);
        assertEquals(15, topPrize.getLevel());
        assertEquals(40_000_000L, topPrize.getPrize());
        assertTrue(topPrize.isSafeSpot());
        
        // Garantált szintek ellenőrzése
        Map<Integer, Long> expectedSafePrizes = Map.of(
            5, 100_000L,
            8, 500_000L,
            11, 3_000_000L,
            13, 10_000_000L,
            15, 40_000_000L
        );
        
        levels.stream()
            .filter(PrizeLevel::isSafeSpot)
            .forEach(level -> {
                assertTrue(expectedSafePrizes.containsKey(level.getLevel()));
                assertEquals(expectedSafePrizes.get(level.getLevel()), level.getPrize());
            });
    }
}