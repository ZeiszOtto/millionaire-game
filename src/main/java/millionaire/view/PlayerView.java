/** @file PlayerView.java */
package millionaire.view;

import millionaire.controller.PlayerController;
import javax.swing.*;
import java.awt.*;

/**
 * @brief Játékos létrehozására szolgáló dialógusablak.
 * 
 * Ez az osztály felelős az új játékos létrehozásához szükséges felhasználói felület megjelenítéséért és kezeléséért. 
 * A felület tartalmazza:
 * - Beviteli mezőt a játékos nevének megadásához
 * - OK és Mégse gombokat a művelet végrehajtásához vagy megszakításához
 */
public class PlayerView extends JDialog {
    private static final int DIALOG_WIDTH = 300;    /**< Az ablak alapértelmezett szélessége */
    private static final int DIALOG_HEIGHT = 150;   /**< Az ablak alapértelmezett magassága */
    
    private final PlayerController controller;      /**< A játékos létrehozását vezérlő controller */
    private JTextField nameField;                   /**< A játékos nevét megadó beviteli mező */
    private JButton okButton;                       /**< Az OK gomb */
    private JButton cancelButton;                   /**< A Mégse gomb */


    /**
     * @brief Konstruktor a PlayerView létrehozásához
     * 
     * Létrehoz egy modális dialógusablakot a játékos
     * nevének bekérésére.
     * 
     * @param parent A szülő ablak
     * @param controller A játékos létrehozását kezelő controller
     */
    public PlayerView(JFrame parent, PlayerController controller) {
        super(parent, "Új Játék", true);
        this.controller = controller;
        setupUI();
    }


    /**
     * @brief Létrehozza és elrendezi az ablak komponenseit
     * 
     * Beállítja:
     * - Az ablak méretét
     * - Az ablak pozícióját
     * - A komponensek elrendezését
     * - A beviteli mezőt
     * - Az OK és Mégse gombokat
     * - Az eseménykezelőket
     */
    private void setupUI() {
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setLocationRelativeTo(getParent());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Kérem adja meg a nevét:");
        nameField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Mégse");
        
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setMaximumSize(new Dimension(200, 25));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        okButton.addActionListener(e -> handleOkButton());
        cancelButton.addActionListener(e -> handleCancelButton());
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(buttonPanel);
        
        add(panel);
    }
    

    /**
     * @brief Az OK gomb eseménykezelője
     * 
     * Ellenőrzi a bevitt nevet és továbbítja a controllernek feldolgozásra.
     */
    private void handleOkButton() {
        String name = nameField.getText().trim();
        controller.createPlayer(name);
    }
    

    /**
     * @brief A Mégse gomb eseménykezelője
     * 
     * Értesíti a controllert a művelet megszakításáról és bezárja az ablakot.
     */
    private void handleCancelButton() {
        controller.cancelPlayerCreation();
    }
    

    /**
     * @brief Hibaüzenet megjelenítése
     * 
     * Felugró ablakban jeleníti meg a hibaüzenetet.
     * 
     * @param message A megjelenítendő hibaüzenet
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Hiba", JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * @brief A játékos nevének lekérdezése
     * 
     * Visszaadja a beviteli mezőbe írt nevet, eltávolítva az esetleges felesleges szóközöket.
     * 
     * @return A megadott játékosnév
     */
    public String getPlayerName() {
        return nameField.getText().trim();
    }


    /**
     * @brief Bezárja a dialógusablakot
     * 
     * Felszabadítja az ablakhoz tartozó erőforrásokat és eltávolítja az ablakot a képernyőről.
     */
    public void closeDialog() {
        dispose();
    }
}