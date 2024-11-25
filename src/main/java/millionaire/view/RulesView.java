/** @file RulesView.java */
package millionaire.view;

import millionaire.controller.RulesController;
import javax.swing.*;
import java.awt.*;

/**
 * @brief A játékszabályok megjelenítésére szolgáló ablak.
 * 
 * Ez az osztály felelős a játékszabályok megjelenítéséért egy görgethetö szövegterületen.
 * Az ablak tartalmazza:
 * - A játékszabályok szövegét
 * - Görgetősávot a hosszabb tartalom kezeléséhez
 * - Bezárás gombot
 */
public class RulesView extends JFrame {
    private static final int WINDOW_WIDTH = 800;                                /**< Az ablak alapértelmezett szélessége */
    private static final int WINDOW_HEIGHT = 600;                               /**< Az ablak alapértelmezett magassága */
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< Az ablak háttérszíne */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< Az ablak szövegszíne */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A gombok háttérszíne */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombokra mutatáskor megváltozó háttérszín */
    
    private final RulesController controller;   /**< A játékszabályok megjelenítését vezérlő controller */
    private JTextArea rulesArea;                /**< A játékszabályokat megjelenítő szövegterület */
    

    /**
     * @brief Konstruktor a RulesView létrehozásához
     * @param controller A szabályokat kezelő controller
     */
    public RulesView(RulesController controller) {
        this.controller = controller;
        setupWindow();
        setupComponents();
    }
    

    /**
     * @brief Beállítja az ablak alapvető tulajdonságait
     * 
     * Beállítja:
     * - Az ablak címét
     * - Az ablak méretét
     * - A bezárás műveletét
     * - Az ablak pozícióját
     * - Az átméretezhetőséget
     */
    private void setupWindow() {
        setTitle("Játékszabályok");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    

    /**
     * @brief Létrehozza és elrendezi az ablak komponenseit
     * 
     * Létrehozza:
     * - A főpanelt
     * - A görgethetö szövegterületet
     * - A bezárás gombot
     */
    private void setupComponents() {
        JPanel mainPanel = createMainPanel();
        JScrollPane scrollPane = createRulesTextArea();
        JButton closeButton = createCloseButton();
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(closeButton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    

    /**
     * @brief Létrehozza az ablak fő paneljét
     * 
     * @return A létrehozott fő panel megfelelő elrendezéssel és háttérszínnel
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }
    

    /**
     * @brief Létrehozza a szabályok megjelenítésére szolgáló szövegterületet
     * 
     * @return A görgetősávval ellátott szövegterület
     */
    private JScrollPane createRulesTextArea() {
        rulesArea = new JTextArea();
        rulesArea.setEditable(false);
        rulesArea.setBackground(BACKGROUND_COLOR);
        rulesArea.setForeground(TEXT_COLOR);
        rulesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        rulesArea.setLineWrap(true);
        rulesArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(rulesArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
    

    /**
     * @brief Létrehozza és konfigurálja a bezárás gombot
     * 
     * @return A létrehozott és stílusozott bezárás gomb
     */
    private JButton createCloseButton() {
        JButton button = new JButton("Bezárás");
        styleButton(button);
        button.addActionListener(e -> controller.handleCloseRequest());
        return button;
    }
    

    /**
     * @brief Stílusbeállításokat alkalmaz egy gombra
     * 
     * @param button A stílusozandó gomb
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }
    

     /**
     * @brief Beállítja a játékszabályok szövegét
     * 
     * @param content A megjelenítendő szabályok szövege
     */
    public void displayRules(String content) {
        rulesArea.setText(content);
        rulesArea.setCaretPosition(0);
    }
    

    /**
     * @brief Hibaüzenet megjelenítése
     * 
     * @param message A megjelenítendő hibaüzenet
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Hiba", JOptionPane.ERROR_MESSAGE);
    }
    

    /**
     * @brief Az ablak bezárása után felszabadítja a hozzá tartozó erőforrásokat.
     */
    public void closeWindow() {
        dispose();
    }
}