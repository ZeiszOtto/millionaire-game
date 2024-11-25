/** @file PlayerController.java */
package millionaire.controller;

import millionaire.model.PlayerModel;
import millionaire.view.PlayerView;
import javax.swing.JFrame;


/**
 * @brief A játékos létrehozását kezelő vezérlő osztály.
 * 
 * Ez az osztály felelős:
 * - A játékos létrehozási folyamat vezérléséért
 * - A játékos nevének validálásáért
 * - A játékos objektum létrehozásáért
 */
public class PlayerController {
    private PlayerView view;        /**< A játékos létrehozási nézet */
    private PlayerModel player;     /**< A létrehozott játékos */
    private boolean wasSuccessful;  /**< A létrehozás sikerességének jelzője */


    /**
     * @brief Konstruktor a PlayerController létrehozásához
     * @param parentFrame A szülő ablak
     */
    public PlayerController(JFrame parentFrame) {
        this.view = new PlayerView(parentFrame, this);
        this.wasSuccessful = false;
    }


    /**
     * @brief Létrehoz egy új játékost
     * 
     * Ellenőrzi a név érvényességét, létrehozza a játékos objektumot.
     * 
     * @param name A játékos neve
     */
    public void createPlayer(String name) {
        if (name.isEmpty()) {
            view.showError("A név nem lehet üres!");
            return;
        }
        
        player = new PlayerModel(name);
        wasSuccessful = true;
        view.closeDialog();
    }
    

    /**
     * @brief Megszakítja a játékos létrehozását
     */
    public void cancelPlayerCreation() {
        wasSuccessful = false;
        view.closeDialog();
    }
    

    /**
     * @brief Megjeleníti a játékos létrehozási dialógust
     * @return A létrehozott játékos, vagy null ha megszakították
     */
    public PlayerModel showPlayerCreationDialog() {
        view.setVisible(true);
        return wasSuccessful ? player : null;
    }
}