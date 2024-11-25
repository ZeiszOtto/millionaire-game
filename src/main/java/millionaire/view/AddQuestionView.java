/** @file AddQuestionView.java */
package millionaire.view;

import millionaire.controller.AddQuestionController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @brief Új kérdések hozzáadására szolgáló grafikus felület.
 * 
 * Ez az osztály felelős az új kérdések hozzáadásához szükséges felhasználói felület
 * megjelenítéséért és kezeléséért. A felület tartalmaz:
 * - Beviteli mezőket a kérdés adatainak megadásához
 * - Legördülő menüket a kategória és nehézség kiválasztásához
 * - Gombokat a művelet végrehajtásához vagy megszakításához
 */
public class AddQuestionView extends JFrame {
    private static final int WINDOW_WIDTH = 800;                                /**< Az ablak alapértelmezett szélessége */
    private static final int WINDOW_HEIGHT = 600;                               /**< Az ablak alapértelmezett magassága */
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< Az ablak háttérszíne */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A gombok alapértelmezett színe */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombokra mutatáskor megjelenő szín */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< A szövegek alapértelmezett színe */
    
    private final AddQuestionController controller;     /**< Az ablakhoz tartozó vezérlő */
    
    private JTextField questionField;                   /**< A kérdés beviteli mezője */
    private JTextField optionAField;                    /**< Az 'A' válasz beviteli mezője */
    private JTextField optionBField;                    /**< A 'B' válasz beviteli mezője */
    private JTextField optionCField;                    /**< A 'C' válasz beviteli mezője */
    private JTextField optionDField;                    /**< A 'D' válasz beviteli mezője */
    private JComboBox<String> correctAnswerCombo;       /**< A helyes válasz kiválasztására szolgáló legördülő menü */
    private JComboBox<String> difficultyCombo;          /**< A nehézség kiválasztására szolgáló legördülő menü */
    private JComboBox<String> categoryCombo;            /**< A kategória kiválasztására szolgáló legördülő menü */
    

    /**
     * @brief Konstruktor az AddQuestionView létrehozásához
     * @param controller A kérdés hozzáadását kezelő controller
     */
    public AddQuestionView(AddQuestionController controller) {
        this.controller = controller;
        setupWindow();
        setupComponents();
        pack();
        setLocationRelativeTo(null);
    }
    

    /**
     * @brief Beállítja az ablak alapvető tulajdonságait
     * 
     * Beállítja:
     * - Az ablak címét
     * - Az ablak méretét
     * - A bezárás műveletét
     * - Az átméretezhetőséget
     * - Az ablak pozícióját
     */
    private void setupWindow() {
        setTitle("Új kérdés hozzáadása");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    

    /**
     * @brief Létrehozza és elrendezi az ablak komponenseit
     * 
     * Létrehozza és elrendezi:
     * - A címkét
     * - Az űrlapot a beviteli mezőkkel
     * - A gombokat
     */
    private void setupComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        addTitle(mainPanel);
        addFormPanel(mainPanel);
        addButtonPanel(mainPanel);
        
        add(mainPanel);
    }
    

    /**
     * @brief Hozzáadja a címet az ablakhoz
     * 
     * Létrehoz és formáz egy címke komponenst,
     * amely az ablak tetején jelenik meg.
     * 
     * @param mainPanel A fő panel, amihez a címke hozzáadásra kerül
     */
    private void addTitle(JPanel mainPanel) {
        JLabel titleLabel = new JLabel("Új kérdés hozzáadása");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    

    /**
     * @brief Létrehozza és hozzáadja az űrlapot az ablakhoz
     * 
     * Az űrlap tartalmazza:
     * - A beviteli mezőket a kérdéshez és válaszokhoz
     * - A legördülő menüket a helyes válasz, nehézség és kategória kiválasztásához
     * 
     * @param mainPanel A fő panel, amihez az űrlap hozzáadásra kerül
     */
    private void addFormPanel(JPanel mainPanel) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        questionField = new JTextField(40);
        optionAField = new JTextField(40);
        optionBField = new JTextField(40);
        optionCField = new JTextField(40);
        optionDField = new JTextField(40);
        correctAnswerCombo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        difficultyCombo = new JComboBox<>(controller.getDifficulties().toArray(new String[0]));
        categoryCombo = new JComboBox<>(controller.getCategories().toArray(new String[0]));
        
        addFormRow(formPanel, gbc, 0, "Kérdés:", questionField);
        addFormRow(formPanel, gbc, 1, "A válasz:", optionAField);
        addFormRow(formPanel, gbc, 2, "B válasz:", optionBField);
        addFormRow(formPanel, gbc, 3, "C válasz:", optionCField);
        addFormRow(formPanel, gbc, 4, "D válasz:", optionDField);
        addFormRow(formPanel, gbc, 5, "Helyes válasz:", correctAnswerCombo);
        addFormRow(formPanel, gbc, 6, "Nehézség:", difficultyCombo);
        addFormRow(formPanel, gbc, 7, "Kategória:", categoryCombo);
        
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    }
    

    /**
     * @brief Egy sornyi űrlapmező hozzáadása
     * 
     * Hozzáad egy címkét és egy komponenst (beviteli mező vagy legördülő lista)
     * az űrlap egy sorához.
     * 
     * @param panel Az űrlap panelje
     * @param gbc A rács elrendezés beállításai
     * @param row A sor száma
     * @param labelText A címke szövege
     * @param component A hozzáadandó komponens
     */
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        
        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_COLOR);
        label.setPreferredSize(new Dimension(100, 25));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(component, gbc);
    }
    

    /**
     * @brief Létrehozza és hozzáadja a gombokat az ablakhoz
     * 
     * Létrehozza a 'Mentés' és 'Mégse' gombokat,
     * beállítja a megjelenésüket és eseménykezelőiket.
     * 
     * @param mainPanel A fő panel, amihez a gombok hozzáadásra kerülnek
     */
    private void addButtonPanel(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton submitButton = createStyledButton("Mentés");
        JButton cancelButton = createStyledButton("Mégse");
        
        submitButton.addActionListener(e -> handleSubmit());
        cancelButton.addActionListener(e -> handleCancel());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel);
    }
    

    /**
     * @brief Létrehoz egy stílusozott gombot
     * 
     * Beállítja a gomb:
     * - Méretét
     * - Betűtípusát
     * - Színeit
     * - Keretét
     * - Egér eseménykezelőit
     * 
     * @param text A gomb felirata
     * @return A létrehozott és stílusozott gomb
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
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
        
        return button;
    }
    

    /**
     * @brief A 'Mentés' gomb eseménykezelője
     * 
     * Összegyűjti az űrlap adatait és továbbítja a controllernek feldolgozásra.
     */
    private void handleSubmit() {
        controller.handleSubmit(
            questionField.getText(),
            optionAField.getText(),
            optionBField.getText(),
            optionCField.getText(),
            optionDField.getText(),
            (String) correctAnswerCombo.getSelectedItem(),
            (String) difficultyCombo.getSelectedItem(),
            (String) categoryCombo.getSelectedItem()
        );
    }
    

    /**
     * @brief A 'Mégse' gomb eseménykezelője
     * 
     * Értesíti a controllert a művelet megszakításáról.
     */
    private void handleCancel() {
        controller.handleCancel();
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
     * @brief Sikeres művelet visszajelzése
     * 
     * Felugró ablakban jeleníti meg a sikeres művelet üzenetét.
     * 
     * @param message A megjelenítendő üzenet
     */
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Siker", JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * @brief Törli az űrlap mezőinek tartalmát
     * 
     * Alaphelyzetbe állítja a beviteli mezőket és a legördülő menüket
     */
    public void clearFields() {
        questionField.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        correctAnswerCombo.setSelectedIndex(0);
        difficultyCombo.setSelectedIndex(0);
        categoryCombo.setSelectedIndex(0);
    }
    

    /**
     * @brief Bezárja az ablakot
     * 
     * Felszabadítja az ablakhoz tartozó erőforrásokat és eltávolítja az ablakot a képernyőről.
     */
    public void closeWindow() {
        dispose();
    }
}