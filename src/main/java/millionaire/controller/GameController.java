/** @file GameController.java */
package millionaire.controller;

import millionaire.model.*;
import millionaire.view.GameView;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.time.Duration;

/**
 * @brief A játék fő vezérlő osztálya.
 * 
 * Ez az osztály felelős a teljes játékmenet vezérléséért:
 * - Koordinálja a játék állapotának változásait
 * - Kezeli a játékos válaszait
 * - Irányítja a segítségek használatát
 * - Frissíti a játék nézetét
 * - Kezeli a játék végét és az eredmények mentését
 */
public class GameController {
    private final GameModel model;                          /**< A játék fő modellje */
    private final GameView view;                            /**< A játék felhasználói felülete */
    private final LifelineController lifelineController;    /**< A segítségek kezelését végző vezérlő */
    private boolean isProcessingAnswer;                     /**< Flag, ami jelzi, hogy éppen feldolgozás alatt áll-e a válasz */
    

    /**
     * @brief Konstruktor a GameController létrehozásához
     * @param player A játékos modellje
     * @param questionRepository A kérdéseket tároló adatbázis
     */
    public GameController(PlayerModel player, QuestionRepository questionRepository) {
        this.model = new GameModel(questionRepository, player);
        this.lifelineController = new LifelineController(this);
        this.view = new GameView(this, lifelineController);
        this.isProcessingAnswer = false;
        
        initializeGame();
    }
    

    /**
     * @brief Inicializálja a játék kezdeti állapotát
     */
    private void initializeGame() {
        updateGameState();
        view.setVisible(true);
    }
    

    /**
     * @brief Kezeli a játékos által választott választ
     * 
     * Letiltja a válaszgombokat a feldolgozás idejére,
     * megjeleníti a válasz helyességét, majd késleltetés után
     * folytatja a játékot vagy befejezi azt.
     * 
     * @param selectedAnswer A választott válasz betűjele
     */
    public void handleAnswer(String selectedAnswer) {
        if (isProcessingAnswer) return;
        isProcessingAnswer = true;
        
        Question currentQuestion = model.getCurrentQuestion();
        boolean isCorrect = currentQuestion.getCorrectAnswer().equals(selectedAnswer);
        
        // 
        view.showAnswerResult(selectedAnswer, currentQuestion.getCorrectAnswer(), isCorrect);
        
        // Process the answer after a delay
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> processAnswer(selectedAnswer));
                timer.cancel();
            }
        }, 1500);
    }
    

    /**
     * @brief Feldolgozza a játékos válaszát
     * 
     * A válasz helyességétől függően:
     * - Helyes válasz esetén frissíti a játék állapotát
     * - Helytelen válasz esetén befejezi a játékot
     * - Utolsó kérdés helyes megválaszolása esetén a játékos nyer
     * 
     * @param selectedAnswer A választott válasz betűjele
     */
    private void processAnswer(String selectedAnswer) {
        GameState result = model.processAnswer(selectedAnswer);
        
        switch (result) {
            case CORRECT:
                updateGameState();
                break;
                
            case WON:
            case LOST:
                handleGameEnd(result);
                break;
        }
        
        isProcessingAnswer = false;
    }
    

    /**
     * @brief Frissíti a játék állapotát
     * 
     * Frissíti:
     * - Az aktuális kérdést
     * - A nyereménylétrát
     * - A segítségek állapotát
     */
    private void updateGameState() {
        Question currentQuestion = model.getCurrentQuestion();
        int currentLevel = model.getPlayer().getCurrentLevel();
        
        view.updateQuestion(currentQuestion);
        view.updatePrizeLadder(currentLevel);
        
        lifelineController.setGameContext(currentQuestion, currentLevel, view.getAnswerButtons());
    }
    

    /**
     * @brief Kezeli a játék végét
     * 
     * - Kiszámítja a végső nyereményt
     * - Megjeleníti a játék végét jelző dialógust
     * - Menti az eredményt
     * - Bezárja a játékablakot
     * 
     * @param result A játék végének állapota (nyert/vesztett)
     */
    private void handleGameEnd(GameState result) {
        PlayerModel player = model.getPlayer();
        
        view.stopTimer();
        
        long finalPrize = result == GameState.WON ? 
            model.getCurrentPrize() : 
            model.getLastSafePrize();
        
        player.setPrize(finalPrize);

        view.updateFinalPlaytime();
        
        view.showGameEndDialog(result, player.getFormattedPrize());
        
        saveScoreAndClose(player);
    }
    

    /**
     * @brief Menti az eredményt és bezárja a játékot
     * 
     * - Elmenti a játékos eredményét a dicsőséglistába
     * - Bezárja a játékablakot
     * - Visszatér a főmenübe
     * 
     * @param player A játékos modellje
     */
    private void saveScoreAndClose(PlayerModel player) {
        ScoreboardController scoreboardController = new ScoreboardController();
        scoreboardController.addScore(player);
        
        view.closeGame();
        new MainMenuController().showMenu();
    }
    

    /**
     * @brief Visszaadja az aktuális játékos modelljét
     * @return A játékos modellje
     */
    public PlayerModel getPlayer() {
        return model.getPlayer();
    }


    /**
     * @brief Frissíti a játékidőt a modellben
     * @param elapsedTime Az eltelt játékidő
     */
    public void updateGameTime(Duration elapsedTime) {
        model.updatePlayTime(elapsedTime);
    }


    /**
     * @brief Ellenőrzi, hogy lejárt-e a játékidő
     * @param currentTime Az aktuális játékidő
     */
    private void checkTimeLimit(Duration currentTime) {
        if (model.isTimeUp(currentTime)) {
            handleGameEnd(GameState.LOST);
        }
    }


    /**
     * @brief Visszaadja az aktuális kérdést
     * @return Az aktuális kérdés
     */
    public Question getCurrentQuestion() {
        return model.getCurrentQuestion();
    }
    

    /**
     * @brief Visszaadja az aktuális szintet
     * @return Az aktuális szint
     */
    public int getCurrentLevel() {
        return model.getPlayer().getCurrentLevel();
    }
    

    /**
     * @brief Beállítja a válaszgombok engedélyezettségi állapotát
     * @param enabled Az engedélyezettség állapota
     */
    public void setAnswerButtonsEnabled(boolean enabled) {
        view.setAnswerButtonsEnabled(enabled);
    }

    
    /**
     * @brief Visszaadja a játék nézetét
     * @return A játék nézete
     */
    public GameView getGameView() {
        return view;
    }
}