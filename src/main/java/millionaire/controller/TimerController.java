/** @file TimerController.java */
package millionaire.controller;

import millionaire.model.TimerModel;
import millionaire.view.TimerView;
import java.time.Duration;
import javax.swing.Timer;

/**
 * @brief Az időmérő vezérlője
 * 
 * Ez az osztály felelős:
 * - Az időmérő működésének vezérléséért
 * - A modell és view közötti kommunikációért
 */
public class TimerController {
    private final TimerModel model;      /**< Az időmérő modellje */
    private final TimerView view;        /**< Az időmérő nézete */
    private final Timer updateTimer;     /**< Az időmérő frissítéséért felelős Timer */

    /**
     * @brief Konstruktor a vezérlő létrehozásához
     */
    public TimerController() {
        this.model = new TimerModel();
        this.view = new TimerView();
        
        // Timer létrehozása 1 másodperces frissítéssel
        this.updateTimer = new Timer(1000, e -> updateTime());
    }

    /**
     * @brief Időmérő frissítése
     */
    private void updateTime() {
        model.updateElapsedTime();
        view.updateDisplay(model.getElapsedTime());
    }

    /**
     * @brief Időmérő indítása
     */
    public void startTimer() {
        model.start();
        updateTimer.start();
    }

    /**
     * @brief Időmérő megállítása
     */
    public void stopTimer() {
        model.stop();
        updateTimer.stop();
    }

    /**
     * @brief Visszaadja az időmérő nézetét
     * @return Az időmérő nézete
     */
    public TimerView getView() {
        return view;
    }

    /**
     * @brief Visszaadja az eltelt időt
     * @return Az eltelt idő Duration objektumként
     */
    public Duration getElapsedTime() {
        return model.getElapsedTime();
    }
}