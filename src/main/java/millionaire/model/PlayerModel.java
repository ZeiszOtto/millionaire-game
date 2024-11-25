/** @file PlayerModel.java */
package millionaire.model;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

public class PlayerModel implements Comparable<PlayerModel> {
    private String name;            /**< A játékos neve */
    private int currentLevel;       /**< A játékos aktuális szintje (1-15) */
    private Duration playtime;      /**< A játékos játékideje */
    private Instant startTime;      /**< A játék kezdetének időpontja */
    private long prize;             /**< A játékos által nyert összeg */
    

     /**
    * @brief Konstruktor új játékos létrehozásához
    * @param name A játékos neve
    */
    public PlayerModel(String name) {
        this.name = name;
        this.currentLevel = 1;
        this.playtime = Duration.ZERO;
        this.startTime = Instant.now();
        this.prize = 0;
    }


    /**
     * @brief Konstruktor meglévő játékos adatainak betöltéséhez
     * @param name A játékos neve
     * @param level A játékos szintje
     * @param prize A játékos nyereménye
     * @param playtime A játékos játékideje
     */
    public PlayerModel(String name, int level, long prize, Duration playtime) {
        this.name = name;
        this.currentLevel = level;
        this.prize = prize;
        this.playtime = playtime;
        this.startTime = Instant.now();
    }


    /**
     * @brief Visszaadja az aktuális játékos nevét.
     * @return A játékos neve.
     */
    public String getName() { return name; }


    /**
     * @brief Visszaadja az aktuális játékos jelenlegi szintjét (1-15).
     * @return A játékos jelenlegi szintje.
     */
    public int getCurrentLevel() { return currentLevel; }


    /**
     * @brief Visszaadja az aktuális játékos játékidejét.
     * @return A játékos játékideje.
     */
    public Duration getPlaytime() { return playtime; }


    /**
     * @brief Visszaadja az aktuális játékos játékának kezdetének időpontját.
     * @return A játék kezdetének időpontja.
     */
    public Instant getStartTime() { return startTime; }


    /**
     * @brief Visszaadja az aktuális játékos nyereményét.
     * @return A játékos nyereménye.
     */
    public long getPrize() { return prize; }


    /**
     * @brief Beállítja az aktuális játékos nevét.
     * @return A játékos neve.
     */
    public void setName(String name) { this.name = name; }


    /**
     * @brief Beállítja az aktuális játékos jelenlegi szintjét.
     * @param currentLevel A játékos jelenlegi szintje.
     */
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }


    /**
     * @brief Beállítja az aktuális játékos játékidejét.
     * @param playtime A játékos játékideje.
     */
    public void setPlaytime(Duration playtime) { this.playtime = playtime; }

    /**
     * @brief Beállítja az aktuális játékos nyereményét.
     * @param prize A játékos nyereménye.
     */
    public void setPrize(long prize) { this.prize = prize; }
    

    /**
     * @brief Játékidő frissítése a kezdési idő alapján
     */
    public void updatePlaytime() {
        if (this.playtime == null || this.playtime.equals(Duration.ZERO)) {
            this.playtime = Duration.between(startTime, Instant.now());
        }
        // Ha a Timer már beállította a játékidejét, akkor ne frissítsük
    }


    /**
     * @brief Növeli az aktuális játékos szintjét eggyel, ha helyesen válaszol egy kérdésre.
     */
    public void incrementLevel() { currentLevel++; }


    /**
     * @brief Visszaadja az aktuális játékos nyereményét formázva.
     * @return A játékos nyereménye formázva.
     */
    public String getFormattedPrize() {
        return NumberFormat.getNumberInstance(new Locale("hu", "HU")).format(prize) + " Ft";
    }


    /**
     * @brief Játékidő formázott megjelenítése
     * @return A játékidő "perc:másodperc" formátumban
     */
    public String getFormattedPlaytime() {
        if (playtime == null) {
            return "00:00";
        }
        long totalSeconds = playtime.getSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    /**
     * @brief Játékosok összehasonlítása először a szintjük, majd a játékidőjük alapján, ha a szintjük megegyezik.
     * @param other A másik játékos, amivel összehasonlítjuk az aktuális játékost.
     * @return Az összehasonlítás eredménye.
     */
    @Override
    public int compareTo(PlayerModel other) {
        int levelComparison = Integer.compare(other.currentLevel, this.currentLevel);
        if (levelComparison != 0) {
            return levelComparison;
        }

        return this.playtime.compareTo(other.playtime);
    }


    /**
     * @brief Két játékos egyenlőségének vizsgálata a nevük alapján.
     * @param obj A másik játékos, amivel összehasonlítjuk az aktuális játékost.
     * @return Az egyenlőség eredménye.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayerModel)) return false;
        PlayerModel other = (PlayerModel) obj;
        return this.name.equals(other.name);
    }
}