/** @file LifelineView.java */
package millionaire.view;

import millionaire.controller.LifelineController;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @brief A játék segítségeit megjelenítő és kezelő felület.
 * 
 * Ez az osztály felelős a játék során használható segítségek (felezés, közönség)
 * grafikus megjelenítéséért és interakcióinak kezeléséért. A felület tartalmazza:
 * - A segítségeket aktiváló gombokat
 * - A segítségek állapotának megjelenítését
 * - A közönség szavazatainak vizualizációját
 */
public class LifelineView {
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< A segítség panel háttere */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< A szövegek színe */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A gombok alapértelmezett színe */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombokra mutatáskor megváltozó szín */
    private static final Color DISABLED_COLOR = new Color(128, 128, 128);       /**< A letiltott gombok színe */
    
    private final JButton fiftyFiftyButton;         /**< A felezés segítség gombja */
    private final JButton audienceButton;           /**< A közönség segítség gombja */
    private final LifelineController controller;    /**< A segítségeket kezelő controller */
    private final JFrame parentFrame;               /**< A szülő ablak */


    /**
     * @brief Konstruktor a LifelineView létrehozásához
     * @param parentFrame A szülő ablak
     * @param controller A segítségeket kezelő controller
     */
    public LifelineView(JFrame parentFrame, LifelineController controller) {
        this.parentFrame = parentFrame;
        this.controller = controller;
        
        this.fiftyFiftyButton = createLifelineButton("Felezés", e -> controller.useFiftyFifty());
        this.audienceButton = createLifelineButton("Közönség szavazás", e -> controller.useAudienceHelp());
    }
    
    
    /**
     * @brief Létrehoz egy segítség gombot
     * 
     * Létrehoz egy gombot a megadott szöveggel és eseménykezelővel, beállítja a gomb stílusát.
     * 
     * @param text A gomb felirata
     * @param listener Az eseménykezelő
     * @return A létrehozott és stílusozott gomb
     */
    private JButton createLifelineButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(listener);
        return button;
    }
    

    /**
     * @brief Stílusbeállításokat alkalmaz egy gombra
     * 
     * Beállítja a gomb:
     * - Betűtípusát és méretét
     * - Színeit
     * - Keretét
     * - Egér eseménykezelőit
     * 
     * @param button A stílusozandó gomb
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER_COLOR);
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });
    }

    

    /**
     * @brief Létrehozza a segítségek paneljét
     * 
     * Létrehozza a segítség gombokat tartalmazó panelt, megfelelő elrendezéssel és stílusbeállításokkal.
     * 
     * @return A létrehozott panel a segítség gombokkal
     */
    public JPanel createLifelinePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(new Color(90, 90, 90));
        panel.add(fiftyFiftyButton);
        panel.add(audienceButton);
        return panel;
    }
    

    /**
     * @brief Frissíti a segítség gombok állapotát
     * 
     * Frissíti a gombok:
     * - Engedélyezettségi állapotát
     * - Színeit
     * A felhasznált segítségek gombjai letiltásra kerülnek.
     * 
     * @param fiftyFiftyAvailable A felezés segítség elérhetősége
     * @param audienceHelpAvailable A közönség segítség elérhetősége
     */
    public void updateButtonStates(boolean fiftyFiftyAvailable, boolean audienceHelpAvailable) {
        fiftyFiftyButton.setEnabled(fiftyFiftyAvailable);
        audienceButton.setEnabled(audienceHelpAvailable);
        
        fiftyFiftyButton.setBackground(fiftyFiftyAvailable ? BUTTON_COLOR : DISABLED_COLOR);
        audienceButton.setBackground(audienceHelpAvailable ? BUTTON_COLOR : DISABLED_COLOR);
    }


    /**
     * @brief Frissíti a válaszgombokat a felezés segítség használatakor
     * 
     * A felezés segítség használatakor:
     * - Letiltja a kiválasztott rossz válaszokat
     * - Törli a letiltott válaszok szövegét
     * 
     * @param answerButtons A válaszgombok tömbje
     * @param visibilityMap A válaszok láthatóságát tartalmazó térkép
     */
    public void updateAnswerButtons(JButton[] answerButtons, Map<String, Boolean> visibilityMap) {
        for (JButton button : answerButtons) {
            String option = button.getText().substring(0, 1);
            if (!visibilityMap.get(option)) {
                button.setEnabled(false);
                button.setText(option + ": ");
            }
        }
    }
    

    /**
     * @brief Megjeleníti a közönség szavazatainak eredményét
     * 
     * Létrehoz egy dialógusablakot, amely megjeleníti a közönség szavazatainak százalékos megoszlását.
     * 
     * @param percentages A válaszokra adott szavazatok százalékos megoszlása
     */
    public void showAudienceResults(Map<String, Integer> percentages) {
        JDialog dialog = createAudienceDialog(percentages);
        dialog.setVisible(true);
    }
    

    /**
     * @brief Létrehozza a közönség szavazatait megjelenítő dialógusablakot
     * 
     * A dialógusablak tartalmazza:
     * - A válaszlehetőségeket
     * - A szavazatok százalékos arányát
     * - Vizuális megjelenítést (oszlopdiagramok)
     * - Bezárás gombot
     * 
     * @param percentages A válaszokra adott szavazatok százalékos megoszlása
     * @return A létrehozott dialógusablak
     */
    private JDialog createAudienceDialog(Map<String, Integer> percentages) {
        JDialog dialog = new JDialog(parentFrame, "Közönség szavazás", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel barsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        barsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (Map.Entry<String, Integer> entry : percentages.entrySet()) {
            barsPanel.add(createPercentageBar(entry.getKey(), entry.getValue()));
        }
        
        JButton closeButton = new JButton("Bezár");
        closeButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(barsPanel, BorderLayout.CENTER);
        dialog.add(closeButton, BorderLayout.SOUTH);
        
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(parentFrame);
        
        return dialog;
    }
    

    /**
     * @brief Létrehoz egy százalékos megjelenítést
     * 
     * Létrehoz egy panelt, amely tartalmazza:
     * - A válasz betűjelét
     * - A százalékos értéket
     * - Egy folyamatjelző sávot
     * 
     * @param letter A válasz betűjele
     * @param percentage A szavazatok százalékos aránya
     * @return A létrehozott panel a százalékos megjelenítéssel
     */
    private JPanel createPercentageBar(String letter, int percentage) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        
        JLabel label = new JLabel(String.format("%s: %d%%", letter, percentage));
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(percentage);
        progressBar.setStringPainted(false);
        
        panel.add(label, BorderLayout.WEST);
        panel.add(progressBar, BorderLayout.CENTER);
        
        return panel;
    }
}