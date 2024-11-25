/** @file Main.java */
package millionaire;

import millionaire.controller.MainMenuController;

/**
 * @brief A "Legyen Ön is Milliomos!" kvízjáték főprogramja.
 * 
 * @author Zeisz Ottó (HP9G2J)
 * @date 2024. november 24.
 * 
 * Ez az osztály a program belépési pontja, amely elindítja a főmenüt és biztosítja, hogy
 * a felhasználói felület az Event Dispatch Thread-en fusson.
 */
public class Main {
    private static boolean developerMode = false;   /**< A fejlesztői mód aktív-e */

     /**
     * @brief Visszaadja, hogy a program fejlesztői módban fut-e
     * @return true ha fejlesztői módban fut, false ha nem
     */
    public static boolean isDeveloperMode() {
        return developerMode;
    }

    /**
     * @brief A program belépési pontja
     * 
     * @param args Parancssori argumentumok: Ha args[0] == "dev", akkor fejlesztői módban indul
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("dev")) {
            developerMode = true;
            System.out.println("Developer mode activated");
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainMenuController controller = new MainMenuController();
            controller.showMenu();
        });
    }
}