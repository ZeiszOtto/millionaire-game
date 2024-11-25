/** @file ScoreboardView.java */
package millionaire.view;

import millionaire.controller.ScoreboardController;
import millionaire.model.PlayerModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * @brief A dicsőséglista megjelenítésére szolgáló ablak.
 * 
 * Ez az osztály felelős a játékosok eredményeinek táblázatos megjelenítéséért.
 * Az ablak tartalmazza:
 * - A játékosok eredményeit tartalmazó táblázatot
 * - Görgetősávot a hosszabb lista kezeléséhez
 * - Bezárás gombot
 */
public class ScoreboardView extends JFrame {
    private static final int WINDOW_WIDTH = 800;                                /**< Az ablak alapértelmezett szélessége */
    private static final int WINDOW_HEIGHT = 600;                               /**< Az ablak alapértelmezett magassága */
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< Az ablak háttérszíne */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< Az ablak szövegszíne */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A gombok háttérszíne */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombokra mutatáskor megváltozó háttérszín */
    
    private final transient ScoreboardController controller;    /**< A dicsőséglista megjelenítését vezérlő controller */
    private final JTable scoreTable;                            /**< A játékosok eredményeit tartalmazó táblázat */     
    private final DefaultTableModel tableModel;                 /**< A táblázat modellje */
    

    /**
     * @brief Konstruktor a ScoreboardView létrehozásához
     * 
     * Inicializálja a táblázatot és beállítja annak modelljét.
     * 
     * @param controller A dicsőséglistát kezelő controller
     */
    public ScoreboardView(ScoreboardController controller) {
        this.controller = controller;
        this.tableModel = createTableModel();
        this.scoreTable = createTable();
        
        setupWindow();
        setupComponents();
    }
    

    /**
     * @brief Beállítja az ablak alapvető tulajdonságait
     */
    private void setupWindow() {
        setTitle("Dicsőséglista");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    

    /**
     * @brief Létrehozza a táblázat adatmodelljét
     * 
     * Létrehozza az oszlopokat:
     * - Helyezés
     * - Név
     * - Megválaszolt kérdések
     * - Nyeremény
     * - Játékidő
     * 
     * @return A létrehozott táblázat modell
     */
    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
            new String[]{"Helyezés", "Név", "Megválaszolt kérdések", "Nyeremény", "Játékidő"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    

    /**
     * @brief Létrehozza a táblázatot, amely a játékosok adatait fogja megjeleníteni.
     * @return A létrehozott táblázat.
     */
    private JTable createTable() {
        JTable table = new JTable(tableModel);
        styleTable(table);
        return table;
    }
    

    /**
     * @brief Konfigurálja a táblázat megjelenését
     * 
     * Beállítja:
     * - A háttérszínt
     * - A szövegszínt
     * - A betűtípusokat
     * - A sorok magasságát
     * - A rács színét
     * 
     * @param table A konfigurálandó táblázat
     */
    private void styleTable(JTable table) {
        table.setBackground(BACKGROUND_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);
        table.setGridColor(new Color(70, 130, 180));
        
        // Nincs átrendezés és átméretezés..
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
    }
    

      /**
     * @brief Létrehozza és elrendezi az ablak komponenseit
     */
    private void setupComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        
        JButton closeButton = createCloseButton();
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(closeButton, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    

    /**
     * @brief Létrehozza és konfigurálja a bezárás gombot
     * 
     * @return A létrehozott és stílusozott bezárás gomb
     */
    private JButton createCloseButton() {
        JButton button = new JButton("Bezárás");
        styleButton(button);
        button.addActionListener(e -> controller.handleClose());
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
     * @brief Frissíti a táblázat tartalmát
     * 
     * Törli a korábbi adatokat és megjeleníti az új eredményeket.
     * 
     * @param players A megjelenítendő játékosok listája
     */
    public void updateScores(List<PlayerModel> players) {
        tableModel.setRowCount(0);
        int rank = 1;
        for (PlayerModel player : players) {
            tableModel.addRow(new Object[]{
                rank++,
                player.getName(),
                player.getCurrentLevel(),
                player.getFormattedPrize(),
                player.getFormattedPlaytime()
            });
        }
    }
    

    /**
     * @brief Az ablak bezárása után felszabadítja a hozzá tartozó erőforrásokat.
     */
    public void closeWindow() {
        dispose();
    }
}