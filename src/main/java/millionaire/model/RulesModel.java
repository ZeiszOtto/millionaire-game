/** @file RulesModel.java */
package millionaire.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* @brief Játékszabályok kezelését végző osztály.
* 
* Ez az osztály felelős:
* - A játékszabályok betöltéséért text fájlból
* - A szabályok tartalmának tárolásáért
* - A betöltés sikerességének ellenőrzéséért
*/
public class RulesModel {
   /** @brief A játékszabályokat tartalmazó fájl elérési útja */
   private static final String RULES_FILE_PATH = "src/main/resources/rules.txt";
   
   /** @brief A betöltött játékszabályok szövege */
   private String rulesContent;
   
   /**
    * @brief Konstruktor a RulesModel létrehozásához
    * 
    * Létrehozáskor azonnal megkísérli betölteni a játékszabályokat.
    */
   public RulesModel() {
       loadRulesContent();
   }
   
   /**
    * @brief Betölti a játékszabályokat a megadott fájlból.
    * 
    * A fájl beolvasása UTF-8 karakterkódolással történik.
    * Hiba esetén a rulesContent hibaüzenetet fog tartalmazni.
    */
   private void loadRulesContent() {
       try {
           Path path = Paths.get(RULES_FILE_PATH);
           rulesContent = Files.readString(path, StandardCharsets.UTF_8);
       } catch (IOException e) {
           rulesContent = "Failed to load rules: " + e.getMessage();
       }
   }
   
   /**
    * @brief Visszaadja a betöltött játékszabályok szövegét
    * @return A szabályok szövege, vagy hibaüzenet ha a betöltés sikertelen volt
    */
   public String getRulesContent() {
       return rulesContent;
   }
   
   /**
    * @brief Ellenőrzi a játékszabályok betöltésének sikerességét
    * @return true ha a szabályok sikeresen betöltődtek, false ha hiba történt
    */
   public boolean isContentLoaded() {
       return rulesContent != null && !rulesContent.startsWith("Failed to load rules");
   }
}