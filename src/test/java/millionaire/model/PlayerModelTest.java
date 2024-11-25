/** @file PlayerTest.java */
package millionaire.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;

/**
 * A PlayerModel osztály tesztesetei.
 * 
 * A tesztek ellenőrzik:
 * - A játékos létrehozását
 * - A játékos adatainak kezelését
 * - A játékidő számítását
 * - A nyeremény kezelését
 * - A játékosok összehasonlítását
 */
public class PlayerModelTest {
    private PlayerModel player;
    private final String TEST_PLAYER_NAME = "Teszt Játékos";

    /**
     * Teszt előkészítése
     * 
     * Létrehoz egy alapértelmezett játékost a tesztek előtt.
     */
    @BeforeEach
    void setUp() {
        player = new PlayerModel(TEST_PLAYER_NAME);
    }

    /**
     * Új játékos létrehozásának tesztelése
     * 
     * Ellenőrzi az alapértelmezett értékek beállítását:
     * - Név
     * - Kezdő szint (1)
     * - Kezdeti játékidő (0)
     * - Kezdeti nyeremény (0)
     */
    @Test
    void testNewPlayerCreation() {
        assertEquals(TEST_PLAYER_NAME, player.getName());
        assertEquals(1, player.getCurrentLevel());
        assertEquals(Duration.ZERO, player.getPlaytime());
        assertEquals(0L, player.getPrize());
        assertNotNull(player.getStartTime());
    }

    /**
     * Játékos létrehozása meglévő adatokkal
     * 
     * Ellenőrzi a játékos létrehozását előre megadott értékekkel:
     * - Név
     * - Szint
     * - Nyeremény
     * - Játékidő
     */
    @Test
    void testPlayerCreationWithExistingData() {
        Duration playTime = Duration.ofMinutes(10);
        PlayerModel existingPlayer = new PlayerModel(TEST_PLAYER_NAME, 5, 100000L, playTime);
        
        assertEquals(TEST_PLAYER_NAME, existingPlayer.getName());
        assertEquals(5, existingPlayer.getCurrentLevel());
        assertEquals(100000L, existingPlayer.getPrize());
        assertEquals(playTime, existingPlayer.getPlaytime());
    }

    /**
     * Játékos szintlépésének tesztelése
     * 
     * Ellenőrzi:
     * - A szint növelését
     * - Többszörös szintlépést
     */
    @Test
    void testLevelIncrement() {
        assertEquals(1, player.getCurrentLevel());
        
        player.incrementLevel();
        assertEquals(2, player.getCurrentLevel());

        player.incrementLevel();
        player.incrementLevel();
        assertEquals(4, player.getCurrentLevel());
    }

    /**
     * Játékidő kezelésének tesztelése
     * 
     * Ellenőrzi:
     * - A játékidő beállítását
     * - A játékidő formázását
     * - A játékidő frissítését
     */
    @Test
    void testPlaytimeHandling() {
        // Játékidő beállítása
        Duration testDuration = Duration.ofSeconds(125); // 2 perc 5 másodperc
        player.setPlaytime(testDuration);
        
        assertEquals(testDuration, player.getPlaytime());
        assertEquals("02:05", player.getFormattedPlaytime());
        
        // Másik időtartam tesztelése
        testDuration = Duration.ofSeconds(3665); // 1 óra 1 perc 5 másodperc
        player.setPlaytime(testDuration);
        assertEquals("61:05", player.getFormattedPlaytime());
    }

    /**
     * Nyeremény kezelésének tesztelése
     * 
     * Ellenőrzi:
     * - A nyeremény beállítását
     * - A nyeremény formázását
     */
    @Test
    void testPrizeHandling() {
        player.setPrize(1000000L);
        
        assertEquals(1000000L, player.getPrize());
        // Unicode escape sequence
        assertEquals("1\u00A0000\u00A0000 Ft", player.getFormattedPrize());
        
        player.setPrize(40000000L);
        assertEquals("40\u00A0000\u00A0000 Ft", player.getFormattedPrize());
    }

    /**
     * Játékosok összehasonlításának tesztelése
     * 
     * Ellenőrzi a compareTo metódus működését:
     * - Különböző szintű játékosok
     * - Azonos szintű, különböző játékidejű játékosok
     */
    @Test
    void testPlayerComparison() {
        PlayerModel player1 = new PlayerModel("Játékos 1", 5, 100000L, Duration.ofMinutes(10));
        PlayerModel player2 = new PlayerModel("Játékos 2", 7, 500000L, Duration.ofMinutes(15));
        PlayerModel player3 = new PlayerModel("Játékos 3", 5, 100000L, Duration.ofMinutes(8));
        
        // Magasabb szintű játékos előrébb van
        assertTrue(player2.compareTo(player1) < 0);
        
        // Azonos szint esetén a rövidebb játékidejű van előrébb
        assertTrue(player3.compareTo(player1) < 0);
    }

    /**
     * Játékosok egyenlőségének tesztelése
     * 
     * Ellenőrzi az equals metódus működését:
     * - Azonos nevű játékosok
     * - Különböző nevű játékosok
     * - Null értékek kezelése
     */
    @Test
    void testPlayerEquality() {
        PlayerModel sameNamePlayer = new PlayerModel(TEST_PLAYER_NAME);
        PlayerModel differentNamePlayer = new PlayerModel("Másik Játékos");
        
        assertEquals(player, sameNamePlayer);
        
        assertNotEquals(player, differentNamePlayer);
        
        assertNotEquals(player, null);
        
        assertNotEquals(player, "Nem játékos objektum");
    }
}