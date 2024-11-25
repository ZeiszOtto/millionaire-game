/** @file LifelineController.java */
package millionaire.controller;

import millionaire.model.LifelineModel;
import millionaire.model.Question;
import millionaire.view.LifelineView;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.Map;


/**
 * @brief A játék segítségeit kezelő vezérlő osztály.
 * 
 * Ez az osztály felelős a játék során használható segítségek kezeléséért:
 * - Felezés segítség használata
 * - Közönség segítségének használata
 * - Segítségek állapotának követése
 * - Segítségek eredményének megjelenítése
 */
public class LifelineController {
    private final LifelineModel model;              /**< A segítségek modelje */
    private final LifelineView view;                /**< A segítségek nézete */
    private final GameController gameController;    /**< A játék vezérlője */
    private Question currentQuestion;               /**< Az aktuális kérdés */
    private int currentLevel;                       /**< Az aktuális szint */
    private JButton[] answerButtons;                /**< A válaszlehetőségek gombjai */
    

    /**
     * @brief Konstruktor a LifelineController létrehozásához
     * @param gameController A fő játékvezérlő
     */
    public LifelineController(GameController gameController) {
        this.gameController = gameController;
        this.model = new LifelineModel();
        this.view = new LifelineView(gameController.getGameView(), this);
    }
    

    /**
     * @brief Beállítja a játék aktuális kontextusát
     * 
     * Frissíti:
     * - Az aktuális kérdést
     * - Az aktuális szintet
     * - A válaszgombokat
     * - A segítségek elérhetőségét
     * 
     * @param question Az aktuális kérdés
     * @param level Az aktuális szint
     * @param buttons A válaszgombok
     */
    public void setGameContext(Question question, int level, JButton[] buttons) {
        this.currentQuestion = question;
        this.currentLevel = level;
        this.answerButtons = buttons;
        updateLifelineAvailability();
    }
    

    /**
     * @brief A felezés segítség használata
     * 
     * Eltávolít két helytelen választ, frissíti a gombok állapotát.
     */
    public void useFiftyFifty() {
        if (!model.isFiftyFiftyAvailable()) {
            return;
        }
        try {
            Map<String, Boolean> result = model.getFiftyFiftyResult(currentQuestion);
            view.updateAnswerButtons(answerButtons, result);
            updateLifelineAvailability();
        } catch (Exception e) {
            System.err.println("Error using fifty-fifty: " + e.getMessage());
        }
    }
    

    /**
     * @brief A közönség segítségének használata
     * 
     * Generál egy szavazási eredményt, megjeleníti az eredményt.
     */
    public void useAudienceHelp() {
        if (!model.isAudienceHelpAvailable()) {
            return;
        }
        try {
            Map<String, Integer> result = model.getAudienceResult(currentQuestion, currentLevel);
            view.showAudienceResults(result);
            updateLifelineAvailability();
        } catch (Exception e) {
            System.err.println("Error using audience help: " + e.getMessage());
        }
    }
    

    /**
     * @brief Frissíti a segítségek elérhetőségét
     */
    private void updateLifelineAvailability() {
            SwingUtilities.invokeLater(() -> {
                view.updateButtonStates(
                    model.isFiftyFiftyAvailable(),
                    model.isAudienceHelpAvailable()
                );
            });
    }



    /**
    * @brief Visszaadja a segítségek paneljét
    * @return A segítségek panelje
    */
    public JPanel getLifelinePanel() {
        return view.createLifelinePanel();
    }
}