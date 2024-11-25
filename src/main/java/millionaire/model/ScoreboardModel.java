/** @file ScoreboardModel.java */
package millionaire.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;

/**
* @brief Dicsőségtábla kezelését végző osztály.
* 
* Ez az osztály felelős:
* - A játékosok eredményeinek betöltéséért JSON fájlból
* - Az eredmények perzisztens tárolásáért
* - A játékosok rangsorolásáért
* - Új eredmények hozzáadásáért és frissítéséért
*/
public class ScoreboardModel {
   /** @brief Az eredményeket tároló JSON fájl elérési útja */
   private static final String JSON_FILE_PATH = "src/main/resources/scoreboard.json";
   
   /** @brief A játékosok eredményeit tároló lista */
   private List<PlayerModel> players;
   
   /** @brief JSON szerializálást végző objektum */
   private final Gson gson;

   /**
    * @brief Konstruktor a ScoreboardModel létrehozásához
    * 
    * Inicializálja a listát és betölti a korábbi eredményeket.
    */
   public ScoreboardModel() {
       this.players = new ArrayList<>();
       this.gson = new GsonBuilder().setPrettyPrinting().create();
       initializeAndLoadScores();
   }

   /**
    * @brief Inicializálja és betölti a korábbi eredményeket
    * 
    * A művelet során:
    * - Létrehozza a szükséges könyvtárstruktúrát
    * - Ellenőrzi a fájl létezését és tartalmát
    * - Betölti a korábbi eredményeket
    * - Érvénytelen fájl esetén újat hoz létre
    */
   private void initializeAndLoadScores() {
       try {
           Files.createDirectories(Paths.get("src/main/resources"));
           
           Path filePath = Paths.get(JSON_FILE_PATH);
           if (!Files.exists(filePath)) {
               Files.write(filePath, "[]".getBytes());
               return;
           }

           String jsonContent = Files.readString(filePath);
           if (jsonContent.trim().isEmpty()) {
               Files.write(filePath, "[]".getBytes());
               return;
           }

           JsonElement jsonElement = JsonParser.parseString(jsonContent);
           if (!jsonElement.isJsonArray()) {
               Files.write(filePath, "[]".getBytes());
               return;
           }

           JsonArray jsonArray = jsonElement.getAsJsonArray();
           for (JsonElement element : jsonArray) {
               JsonObject playerObj = element.getAsJsonObject();
               PlayerModel player = new PlayerModel(
                   playerObj.get("name").getAsString(),
                   playerObj.get("level").getAsInt(),
                   playerObj.get("prize").getAsLong(),
                   Duration.ofSeconds(playerObj.get("playtime").getAsLong())
               );
               players.add(player);
           }

           sortPlayers();
       } catch (Exception e) {
           System.err.println("Error initializing scoreboard: " + e.getMessage());
           players = new ArrayList<>();
           try {
               Files.write(Paths.get(JSON_FILE_PATH), "[]".getBytes());
           } catch (IOException ioe) {
               System.err.println("Could not create scoreboard file: " + ioe.getMessage());
           }
       }
   }

   /**
    * @brief Új eredmény hozzáadása vagy meglévő frissítése
    * 
    * Ha a játékos már szerepel a listában és az új eredménye jobb,
    * akkor frissíti a rekordját. Ha még nem szerepelt, vagy rosszabb
    * az új eredménye, akkor az új eredményt menti el.
    * 
    * @param newPlayer Az új játékos eredménye
    */
   public void addScore(PlayerModel newPlayer) {
       Optional<PlayerModel> existingPlayer = players.stream()
           .filter(p -> p.getName().equals(newPlayer.getName()))
           .findFirst();

       if (existingPlayer.isPresent()) {
           PlayerModel old = existingPlayer.get();
           if (shouldReplaceScore(newPlayer, old)) {
               players.remove(old);
               players.add(newPlayer);
           }
       } else {
           players.add(newPlayer);
       }

       sortPlayers();
       saveScores();
   }

   /**
    * @brief Eredmények összehasonlítása
    * 
    * Egy eredmény akkor jobb, ha:
    * - Magasabb szintig jutott, vagy
    * - Ugyanazon a szinten rövidebb idő alatt teljesített
    * 
    * @param newPlayer Az új eredmény
    * @param oldPlayer A korábbi eredmény
    * @return true ha az új eredmény jobb, false ha nem
    */
   private boolean shouldReplaceScore(PlayerModel newPlayer, PlayerModel oldPlayer) {
       return newPlayer.getCurrentLevel() > oldPlayer.getCurrentLevel() || 
              (newPlayer.getCurrentLevel() == oldPlayer.getCurrentLevel() && 
               newPlayer.getPlaytime().compareTo(oldPlayer.getPlaytime()) < 0);
   }

   /**
    * @brief Játékosok rendezése eredmény szerint
    * 
    * A rendezés szempontjai:
    * 1. Elért szint (csökkenő sorrend)
    * 2. Játékidő (növekvő sorrend)
    */
   private void sortPlayers() {
       Collections.sort(players);
   }

   /**
    * @brief Játékosok listájának lekérdezése
    * @return A játékosok listájának másolata
    */
   public List<PlayerModel> getPlayers() {
       return new ArrayList<>(players);
   }

   /**
    * @brief Eredmények mentése fájlba
    * 
    * Az eredményeket JSON formátumban menti, amely tartalmazza
    * minden játékos nevét, szintjét, nyereményét és játékidejét.
    */
    private void saveScores() {
        try {
            JsonArray jsonArray = new JsonArray();
            for (PlayerModel player : players) {
                JsonObject playerObj = new JsonObject();
                playerObj.addProperty("name", player.getName());
                playerObj.addProperty("level", player.getCurrentLevel());
                playerObj.addProperty("prize", player.getPrize());
                // Store the actual seconds value
                playerObj.addProperty("playtime", player.getPlaytime().getSeconds());
                jsonArray.add(playerObj);
            }
    
            Files.createDirectories(Paths.get("src/main/resources"));
            
            Files.write(
                Paths.get(JSON_FILE_PATH), 
                gson.toJson(jsonArray).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            System.err.println("Error saving scoreboard: " + e.getMessage());
        }
    }
}