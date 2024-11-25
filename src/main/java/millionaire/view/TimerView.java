/** @file TimerView.java */
package millionaire.view;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

/**
 * @brief Az időmérő megjelenítése
 * 
 * Ez az osztály felelős:
 * - Az időmérő grafikus megjelenítéséért
 * - Az idő formázott kijelzéséért
 */
public class TimerView extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);
    private static final Color TEXT_COLOR = Color.WHITE;
    
    private final JLabel timeLabel;      /**< Az időt megjelenítő címke */

    /**
     * @brief Konstruktor a nézet létrehozásához
     */
    public TimerView() {
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(200, 50));

        timeLabel = new JLabel("Játékidő: 00:00");
        timeLabel.setForeground(TEXT_COLOR);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(timeLabel);
    }

    /**
     * @brief Az idő kijelzésének frissítése
     * @param duration Az eltelt idő
     */
    public void updateDisplay(Duration duration) {
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        seconds = seconds % 60;
        
        timeLabel.setText(String.format("Játékidő: %02d:%02d", minutes, seconds));
    }
}