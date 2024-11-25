/** @file GameView.java */
package millionaire.view;

import millionaire.Main;
import millionaire.controller.GameController;
import millionaire.controller.LifelineController;
import millionaire.controller.TimerController;
import millionaire.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * @brief A játék fő grafikus felülete.
 * 
 * Ez az osztály felelős a játék fő képernyőjének megjelenítéséért és kezeléséért. 
 * A felület tartalmazza:
 * - A kérdés megjelenítését
 * - A négy válaszlehetőség gombjait
 * - A nyereménylétrát
 * - A segítségek ikonjait
 * - A játékállapot megjelenítését
 */
public class GameView extends JFrame {
    private static final int WINDOW_WIDTH = 1440;                               /**< Az ablak alapértelmezett szélessége */
    private static final int WINDOW_HEIGHT = 720;                               /**< Az ablak alapértelmezett magassága */
    private static final Color BACKGROUND_COLOR = new Color(90, 90, 90);        /**< Az ablak háttérszíne */
    private static final Color BUTTON_COLOR = new Color(70, 130, 180);          /**< A gombok alapértelmezett színe */
    private static final Color BUTTON_HOVER_COLOR = new Color(100, 149, 237);   /**< A gombokra mutatáskor megváltozó szín */
    private static final Color TEXT_COLOR = Color.WHITE;                        /**< A szövegek alapértelmezett színe */
    private static final Color HIGHLIGHT_COLOR = new Color(255, 215, 0);        /**< A kiemelt nyereménytábla színe */
    private static final Color CORRECT_ANSWER_COLOR = new Color(50, 205, 50);   /**< A helyes válasz színe */
    private static final Color WRONG_ANSWER_COLOR = new Color(220, 20, 20);    /**< A helytelen válasz színe */
    
    private final GameController controller;        /**< A játékot vezérlő controller */
    private final TimerController timerController;  /**< Az időmérő vezérlője */
    private JLabel questionLabel;                   /**< A kérdés szövegét megjelenítő címke */
    private JButton[] answerButtons;                /**< A válaszlehetőségek gombjai */
    private final List<JPanel> prizeLadderRows;     /**< A nyereménytábla sorait tartalmazó lista */
    private final LifelineView lifelineView;        /**< A segítségeket kezelő nézet */
    private JLabel devHintLabel;                    /**< Fejlesztői mód súgó címke */

    

    /**
     * @brief Konstruktor a GameView létrehozásához
     * @param controller A játékot vezérlő controller
     * @param lifelineController A segítségeket kezelő controller
     */
    public GameView(GameController controller, LifelineController lifelineController) {
        this.controller = controller;
        this.timerController = new TimerController();
        this.prizeLadderRows = new ArrayList<>();
        this.lifelineView = new LifelineView(this, lifelineController);
        
        setupWindow();
        setupComponents();
        timerController.startTimer();
    }
    

    /**
     * @brief Beállítja az ablak alapvető tulajdonságait
     * 
     * Beállítja:
     * - Az ablak címét ("Legyen Ön is Milliomos!")
     * - Az ablak méretét
     * - A bezárás műveletét
     * - Az átméretezhetőséget
     * - Az ablak pozícióját
     * - Az ablak elrendezését (BorderLayout)
     */
    private void setupWindow() {
        setTitle("Legyen Ön is Milliomos!");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
    }
    

    /**
     * @brief Létrehozza és elrendezi az ablak fő komponenseit
     * 
     * Létrehozza és elrendezi:
     * - A kérdés panelt
     * - A válaszgombok panelt
     * - A segítségek panelt
     * - A nyereménylétra panelt
     */
    private void setupComponents() {
        JPanel gamePanel = new JPanel(new BorderLayout(20, 20));
        gamePanel.setBackground(BACKGROUND_COLOR);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel questionPanel = createQuestionPanel();
        gamePanel.add(questionPanel, BorderLayout.NORTH);
        
        JPanel answersPanel = createAnswersPanel();
        gamePanel.add(answersPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(BACKGROUND_COLOR);
        
        southPanel.add(lifelineView.createLifelinePanel(), BorderLayout.CENTER);
        
        if (Main.isDeveloperMode()) {
            devHintLabel = new JLabel();
            devHintLabel.setForeground(TEXT_COLOR);
            devHintLabel.setHorizontalAlignment(SwingConstants.CENTER);
            devHintLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            southPanel.add(devHintLabel, BorderLayout.SOUTH);
        }
        
        gamePanel.add(southPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        
        rightPanel.add(timerController.getView(), BorderLayout.NORTH);
        
        rightPanel.add(createPrizeLadderPanel(), BorderLayout.CENTER);
        
        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    
    /**
     * @brief Létrehozza a kérdés megjelenítésére szolgáló panelt
     * 
     * A panel tartalmazza a kérdés szövegét megjelenítő címkét,
     * megfelelő formázással és elrendezéssel.
     * 
     * @return A létrehozott kérdés panel
     */
    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(800, 100));
        
        questionLabel = new JLabel();
        questionLabel.setForeground(TEXT_COLOR);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(questionLabel);
        
        return panel;
    }
    

    /**
     * @brief Létrehozza a válaszgombok paneljét
     * 
     * Létrehoz négy gombot (A, B, C, D), beállítja azok:
     * - Megjelenését
     * - Elrendezését
     * - Eseménykezelőit
     * 
     * @return A létrehozott válaszgombok panel
     */
    private JPanel createAnswersPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        
        answerButtons = new JButton[4];
        String[] letters = {"A", "B", "C", "D"};
        
        for (int i = 0; i < 4; i++) {
            final String letter = letters[i];
            answerButtons[i] = createAnswerButton(letter);
            answerButtons[i].addActionListener(e -> controller.handleAnswer(letter));
            panel.add(answerButtons[i]);
        }
        
        return panel;
    }
    

    /**
     * @brief Létrehoz egy válaszgombot
     * 
     * @param letter A válasz betűjele
     * @return A létrehozott és stílusozott válaszgomb
     */
    private JButton createAnswerButton(String letter) {
        JButton button = new JButton(letter);
        styleButton(button);
        return button;
    }
    

    /**
     * @brief Létrehozza a nyereménylétra panelt
     * 
     * A panel tartalmazza:
     * - A 15 nyereményszint megjelenítését
     * - A garantált összegek kiemelését
     * - Az aktuális szint jelölését
     * 
     * @return A létrehozott nyereménylétra panel
     */
    private JPanel createPrizeLadderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        List<PrizeLevel> prizeLevels = GameModel.getPrizeLevels();
        
        for (PrizeLevel level : prizeLevels) {
            JPanel prizeRow = createPrizeRow(level);
            prizeLadderRows.add(prizeRow);
            panel.add(prizeRow);
            if (prizeLevels.indexOf(level) < prizeLevels.size() - 1) {
                panel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        return panel;
    }


    /**
     * @brief Létrehoz egy nyereménylétra sort
     * 
     * @param level A nyereményszint adatai
     * @return A létrehozott nyereménylétra sor
     */
    private JPanel createPrizeRow(PrizeLevel level) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        
        JLabel levelLabel = new JLabel(String.valueOf(level.getLevel()));
        JLabel prizeLabel = new JLabel(formatPrize(level.getPrize()));
        
        levelLabel.setForeground(TEXT_COLOR);
        prizeLabel.setForeground(TEXT_COLOR);
        
        levelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        prizeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        panel.add(levelLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(prizeLabel);
        
        return panel;
    }
    

     /**
     * @brief Formázza a nyereményösszegeket
     * 
     * Az összegeket forintként jeleníti meg (pl. "1.000.000 Ft")
     * 
     * @param prize A formázandó összeg
     * @return A formázott összeg szövegesen
     */
    private String formatPrize(long prize) {
        return String.format("%,d Ft", prize).replace(",", ".");
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
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
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
     * @brief Frissíti a kérdést és a válaszlehetőségeket
     * 
     * Megjeleníti:
     * - Az új kérdés szövegét
     * - Az új válaszlehetőségeket
     * - Alaphelyzetbe állítja a válaszgombokat
     * 
     * @param question Az új kérdés objektum
     */
    public void updateQuestion(Question question) {
        questionLabel.setText(question.getQuestion());
        
        answerButtons[0].setText("A: " + question.getOptionA());
        answerButtons[1].setText("B: " + question.getOptionB());
        answerButtons[2].setText("C: " + question.getOptionC());
        answerButtons[3].setText("D: " + question.getOptionD());
        
        if (Main.isDeveloperMode()) {
            devHintLabel.setText("Válasz:" + question.getCorrectAnswer());
        }

        resetAnswerButtons();
    }
    

    /**
     * @brief Megjeleníti a választott válasz eredményét
     * 
     * - Letiltja a válaszgombokat
     * - Színezi a választott választ (zöld/piros)
     * - Helytelen válasz esetén megjelöli a helyes választ
     * 
     * @param selectedAnswer A választott válasz betűjele
     * @param correctAnswer A helyes válasz betűjele
     * @param isCorrect A válasz helyességét jelző flag
     */
    public void showAnswerResult(String selectedAnswer, String correctAnswer, boolean isCorrect) {
        setAnswerButtonsEnabled(false);
        
        for (JButton button : answerButtons) {
            String buttonLetter = button.getText().substring(0, 1);
            if (buttonLetter.equals(selectedAnswer)) {
                button.setBackground(isCorrect ? CORRECT_ANSWER_COLOR : WRONG_ANSWER_COLOR);
            } else if (buttonLetter.equals(correctAnswer) && !isCorrect) {
                button.setBackground(CORRECT_ANSWER_COLOR);
            }
        }
    }
    

    /**
     * @brief Frissíti a nyereménylétrát
     * 
     * Kiemeli az aktuális szintet és frissíti a megjelenítést
     * 
     * @param currentLevel Az aktuális szint száma
     */
    public void updatePrizeLadder(int currentLevel) {
        for (int i = 0; i < prizeLadderRows.size(); i++) {
            JPanel row = prizeLadderRows.get(i);
            int rowLevel = 15 - i;
            
            if (rowLevel == currentLevel) {
                row.setBackground(HIGHLIGHT_COLOR);
                for (Component comp : row.getComponents()) {
                    if (comp instanceof JLabel) {
                        ((JLabel) comp).setForeground(Color.BLACK);
                    }
                }
            } else {
                row.setBackground(BACKGROUND_COLOR);
                for (Component comp : row.getComponents()) {
                    if (comp instanceof JLabel) {
                        ((JLabel) comp).setForeground(TEXT_COLOR);
                    }
                }
            }
        }
    }
    

    /**
     * @brief Visszaállítja a válaszgombokat alaphelyzetbe
     * 
     * Visszaállítja:
     * - A gombok színeit
     * - A gombok engedélyezettségi állapotát
     */
    private void resetAnswerButtons() {
        for (JButton button : answerButtons) {
            button.setBackground(BUTTON_COLOR);
            button.setEnabled(true);
        }
    }
    

    /**
     * @brief Beállítja a válaszgombok engedélyezettségi állapotát
     * 
     * @param enabled Az engedélyezettség állapota
     */
    public void setAnswerButtonsEnabled(boolean enabled) {
        for (JButton button : answerButtons) {
            button.setEnabled(enabled);
        }
    }
    

    /**
     * @brief Visszaadja a válaszgombokat
     * 
     * @return A válaszgombok tömbje
     */
    public JButton[] getAnswerButtons() {
        return answerButtons;
    }


    /**
     * @brief Leállítja az időmérőt
     */
    public void stopTimer() {
        timerController.stopTimer();
    }
    

    /**
     * @brief Frissíti a végleges játékidejét a játékosnak
     */
    public void updateFinalPlaytime() {
        controller.getPlayer().setPlaytime(timerController.getElapsedTime());
    }
    

    /**
     * @brief Megjeleníti a játék végeredményét
     * 
     * Felugró ablakban jeleníti meg:
     * - A játék végeredményét (nyert/vesztett)
     * - Az elért nyereményt
     * 
     * @param state A játék végállapota
     * @param prize Az elért nyeremény
     */
    public void showGameEndDialog(GameState state, String prize) {
        String message = state == GameState.WON ?
            "Gratulálok! Megnyerted a főnyereményt: " + prize :
            "Sajnos helytelen válasz! Nyereményed: " + prize;
        
        String title = state == GameState.WON ? "Győzelem!" : "Játék vége";
        
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * @brief Bezárja a játék ablakát
     * 
     * Felszabadítja az erőforrásokat és
     * eltávolítja az ablakot a képernyőről.
     */
    public void closeGame() {
        timerController.stopTimer();
        controller.getPlayer().updatePlaytime();
        dispose();
    }
}