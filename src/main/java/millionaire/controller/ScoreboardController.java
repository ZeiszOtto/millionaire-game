/** @file ScoreboardController.java */
package millionaire.controller;

import millionaire.model.ScoreboardModel;
import millionaire.model.PlayerModel;
import millionaire.view.ScoreboardView;

/**
 * @brief A dicsőséglista kezelését végző vezérlő osztály.
 * 
 * Ez az osztály felelős:
 * - A játékosok eredményeinek kezeléséért és megjelenítéséért
 * - Új eredmények hozzáadásáért a dicsőséglistához
 * - A dicsőséglista frissítéséért és megjelenítéséért
 * - A dicsőséglista ablak életciklusának kezeléséért
 */
public class ScoreboardController {
    private final ScoreboardModel model;    /**< A dicsőséglista adatmodellje */
    private final ScoreboardView view;      /**< A dicsőséglista megjelenítési felülete */


    /**
     * @brief Konstruktor a ScoreboardController létrehozásához
     * 
     * Inicializálja a modellt és a nézetet, majd
     * meghívja a refreshScoreboard() metódust a kezdeti
     * megjelenítéshez.
     */
    public ScoreboardController() {
        this.model = new ScoreboardModel();
        this.view = new ScoreboardView(this);
        refreshScoreboard();
    }

    /**
     * @brief Új eredmény hozzáadása a dicsőséglistához
     * 
     * A metódus:
     * 1. Hozzáadja az új játékos eredményét a modellhez
     * 2. Frissíti a dicsőséglista megjelenítését
     * 
     * @param player Az új eredménnyel rendelkező játékos modellje
     */
    public void addScore(PlayerModel player) {
        model.addScore(player);
        refreshScoreboard();
    }
    

    /**
     * @brief Frissíti a dicsőséglista megjelenítését
     * 
     * Lekérdezi a modellből az aktuális eredményeket és
     * frissíti a nézetet az új adatokkal.
     */
    private void refreshScoreboard() {
        view.updateScores(model.getPlayers());
    }
    

    /**
     * @brief Megjeleníti a dicsőséglista ablakot
     * 
     * 1. Frissíti a dicsőséglistát a legújabb adatokkal
     * 2. Láthatóvá teszi a dicsőséglistát tartalmazó ablakot
     */
    public void showScoreboard() {
        refreshScoreboard();
        view.setVisible(true);
    }
    

    /**
     * @brief Kezeli az ablak bezárásának kérését
     * 
     * Meghívja a nézet closeWindow() metódusát az ablak bezárásához és az erőforrások felszabadításához.
     */
    public void handleClose() {
        view.closeWindow();
    }
}