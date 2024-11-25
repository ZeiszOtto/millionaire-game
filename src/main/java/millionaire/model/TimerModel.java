/** @file TimerModel.java */
package millionaire.model;

import java.time.Duration;
import java.time.Instant;

/**
 * @brief Az időmérő adatmodellje
 * 
 * Ez az osztály felelős:
 * - A játékidő tárolásáért
 * - Az időmérés állapotának kezeléséért
 */
public class TimerModel {
    private final Instant startTime;     /**< A játék kezdési időpontja */
    private Duration elapsedTime;        /**< Az eltelt játékidő */
    private boolean isRunning;           /**< Az időmérő fut-e */

    /**
     * @brief Konstruktor az időmérő létrehozásához
     */
    public TimerModel() {
        this.startTime = Instant.now();
        this.elapsedTime = Duration.ZERO;
        this.isRunning = false;
    }

    /**
     * @brief Frissíti az eltelt időt
     */
    public void updateElapsedTime() {
        if (isRunning) {
            elapsedTime = Duration.between(startTime, Instant.now());
        }
    }

    /**
     * @brief Elindítja az időmérőt
     */
    public void start() {
        isRunning = true;
    }

    /**
     * @brief Megállítja az időmérőt
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * @brief Visszaadja az eltelt időt
     * @return Az eltelt idő Duration objektumként
     */
    public Duration getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @brief Visszaadja a kezdési időpontot
     * @return A kezdési időpont Instant objektumként
     */
    public Instant getStartTime() {
        return startTime;
    }
}