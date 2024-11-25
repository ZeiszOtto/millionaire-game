/** @file MainMenuController.java */
package millionaire.controller;

import millionaire.model.*;
import millionaire.view.MainMenuView;


/**
 * @brief A főmenü vezérlő osztálya.
 * 
 * Ez az osztály felelős a főmenü működésének vezérléséért:
 * - Új játék indítása
 * - Kérdés hozzáadási felület megnyitása
 * - Dicsőséglista megjelenítése
 * - Játékszabályok megjelenítése
 * - Játékból való kilépés
 */
public class MainMenuController {
    private final MainMenuModel model;  /**< A főmenü modellje */
    private final MainMenuView view;    /**< A főmenü nézete */
    
    /**
     * @brief Konstruktor a MainMenuController létrehozásához
     */
    public MainMenuController() {
        this.model = new MainMenuModel();
        this.view = new MainMenuView(this);
    }
    

    /**
     * @brief Új játék indítása
     * 
     * - Megjeleníti a játékos létrehozási dialógust
     * - Sikeres létrehozás esetén elindítja a játékot
     */
    public void startNewGame() {
        PlayerController playerController = new PlayerController(view);
        PlayerModel player = playerController.showPlayerCreationDialog();
        
        if (player != null) {
            view.hideWindow();
            GameController gameController = new GameController(player, model.getQuestionRepository());
        }
    }
    

    /**
     * @brief Megjeleníti a kérdés hozzáadási dialógust
     */
    public void showAddQuestionDialog() {
        AddQuestionController addQuestionController = 
            new AddQuestionController(model.getQuestionRepository());
        addQuestionController.showDialog();
    }
    

    /**
     * @brief Megjeleníti a dicsőséglistát
     */
    public void showScoreboard() {
        ScoreboardController scoreboardController = new ScoreboardController();
        scoreboardController.showScoreboard();
    }
    

    /**
     * @brief Megjeleníti a játékszabályokat
     */
    public void showRules() {
        RulesController rulesController = new RulesController();
        rulesController.showRules();
    }
    

    /**
     * @brief Kilép a játékból
     */
    public void exitGame() {
        System.exit(0);
    }
    

    /**
     * @brief Megjeleníti a főmenüt
     */
    public void showMenu() {
        view.showWindow();
    }
}
