/** @file MainMenuView.java */
package millionaire.view;

import millionaire.controller.MainMenuController;
import javax.swing.*;
import java.awt.*;

/**
 * @brief A játék főmenüjének grafikus felülete.
 * 
 * Ez az osztály felelős a játék főmenüjének megjelenítéséért és kezeléséért.
 * A főmenü tartalmazza:
 * - A játék címét
 * - Az összes főmenü opciót (új játék, kérdés hozzáadása, dicsőséglista, kézikönyv, kilépés)
 * - Az opciók közötti navigáció kezelését
 */
public class MainMenuView extends JFrame {
    private static final int WINDOW_WIDTH = 1280;                               /**< Az ablak alapértelmezett szélessége */
    private static final int WINDOW_HEIGHT = 720;                               /**< Az ablak alapértelmezett magassága */
    private static final String GAME_TITLE = "Legyen Ön is Milliomos!";         /**< A játék címe */
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< Az ablak háttérszíne */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A menü gombjainak alapértelmezett színe */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombok színe, amikor az egér fölöttük van */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< A szöveg színe */
    
    private final MainMenuController controller;    /**< A főmenüt kezelő controller */
    

    /**
     * @brief Konstruktor a MainMenuView létrehozásához
     * 
     * Inicializálja az ablakot és beállítja annak komponenseit.
     * 
     * @param controller A főmenüt vezérlő controller
     */
    public MainMenuView(MainMenuController controller) {
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
     * - Az átméretezhetőséget
     * - Az ablak pozícióját a képernyő közepén
     */
    private void setupWindow() {
        setTitle(GAME_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    

    /**
     * @brief Létrehozza és elrendezi az ablak fő komponenseit
     * 
     * Létrehozza és elrendezi:
     * - A játék címét megjelenítő címkét
     * - A menügombokat tartalmazó panelt
     */
    private void setupComponents() {
        JPanel mainPanel = createMainPanel();
        JLabel titleLabel = createTitleLabel();
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(titleLabel);
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }
    

    /**
     * @brief Létrehozza az ablak fő paneljét
     * 
     * A fő panel tartalmazza az összes komponenst
     * megfelelő elrendezéssel és térközökkel.
     * 
     * @return A létrehozott fő panel
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 25, 50, 25));
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }
    

    /**
     * @brief Létrehozza a címet megjelenítő címkét
     * 
     * A címke tartalmazza a játék nevét,
     * megfelelő betűtípussal és mérettel formázva.
     * 
     * @return A létrehozott címke
     */
    private JLabel createTitleLabel() {
        JLabel label = new JLabel(GAME_TITLE);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }


    /**
     * @brief Létrehozza a menügombokat tartalmazó panelt
     * 
     * A panel tartalmazza az összes menüopciót reprezentáló gombot:
     * - Új Játék
     * - Kérdés hozzáadása
     * - Dicsőséglista
     * - Kézikönyv
     * - Kilépés
     * 
     * @return A létrehozott gombpanel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        String[] buttonLabels = {"Új Játék", "Kérdés hozzáadása", "Dicsőséglista", "Kézikönyv", "Kilépés"};
        
        for (String label : buttonLabels) {
            JButton button = createMenuButton(label);
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        
        return panel;
    }
    

    /**
     * @brief Létrehoz egy menügombot
     * 
     * Létrehoz egy gombot a megadott szöveggel,
     * beállítja annak stílusát és eseménykezelőjét.
     * 
     * @param text A gomb felirata
     * @return A létrehozott és stílusozott gomb
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> handleButtonClick(text));
        return button;
    }
    

    /**
     * @brief Stílusbeállításokat alkalmaz egy gombra
     * 
     * Beállítja a gomb:
     * - Méretét
     * - Betűtípusát
     * - Színeit
     * - Keretét
     * - Egér eseménykezelőit
     * - Igazítását
     * 
     * @param button A stílusozandó gomb
     */
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
     * @brief Kezeli a menügombokra való kattintást
     * 
     * A gomb szövegétől függően meghívja a megfelelő
     * controller metódust:
     * - startNewGame()
     * - showAddQuestionDialog()
     * - showScoreboard()
     * - showRules()
     * - exitGame()
     * 
     * @param buttonText A megnyomott gomb szövege
     */
    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "Új Játék":
                controller.startNewGame();
                break;
            case "Kérdés hozzáadása":
                controller.showAddQuestionDialog();
                break;
            case "Dicsőséglista":
                controller.showScoreboard();
                break;
            case "Kézikönyv":
                controller.showRules();
                break;
            case "Kilépés":
                controller.exitGame();
                break;
        }
    }
    

    /**
     * @brief Elrejti a főmenü ablakát
     * 
     * Az ablakot láthatatlanná teszi, de nem zárja be. Új játék indításakor használatos.
     */
    public void hideWindow() {
        setVisible(false);
    }
    

    /**
     * @brief Megjeleníti a főmenü ablakát
     * 
     * Az ablakot láthatóvá teszi. A játékból való visszatéréskor használatos.
     */
    public void showWindow() {
        setVisible(true);
    }
}