/** @file RulesController.java */
package millionaire.controller;

import millionaire.model.RulesModel;
import millionaire.view.RulesView;

/**
 * @brief A játékszabályokat kezelő vezérlő osztály.
 * 
 * Ez az osztály felelős:
 * - A játékszabályok betöltéséért
 * - A szabályok megjelenítéséért
 * - A szabályok ablak kezeléséért
 */
public class RulesController {
    private final RulesModel model;     /**< A játékszabályok modellje */
    private final RulesView view;       /**< A játékszabályok nézete */
    

    /**
     * @brief A játékszabályok vezérlőjének konstruktora
     * 
     * Létrehozza a játékszabályok modelljét és nézetét, majd inicializálja a nézetet.
     */
    public RulesController() {
        this.model = new RulesModel();
        this.view = new RulesView(this);
        initializeView();
    }
    

    /**
     * @brief Inicializálja a játékszabályok nézetét
     * 
     * Ellenőrzi, hogy a szabályok sikeresen betöltődtek-e:
     * - Siker esetén megjeleníti a szabályokat
     * - Hiba esetén hibaüzenetet jelenít meg
     */
    private void initializeView() {
        if (model.isContentLoaded()) {
            view.displayRules(model.getRulesContent());
        } else {
            view.showError("Nem sikerült betölteni a játékszabályokat!");
        }
    }
    

     /**
     * @brief Kezeli az ablak bezárásának kérését
     * 
     * Meghívja a nézet closeWindow() metódusát az ablak bezárásához és az erőforrások felszabadításához.
     */
    public void handleCloseRequest() {
        view.closeWindow();
    }
    

    /**
     * @brief Megjeleníti a játékszabályok ablakot
     * 
     * Láthatóvá teszi a játékszabályokat tartalmazó ablakot a felhasználói felületen.
     */
    public void showRules() {
        view.setVisible(true);
    }
}