/** @file LifelineModelTest.java */
package millionaire.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * A LifelineModel osztály tesztesetei.
 * 
 * A tesztek ellenőrzik:
 * - A felezés segítség működését
 * - A közönség segítségének működését
 * - A segítségek elérhetőségét
 * - A segítségek többszöri használatának tiltását
 */
public class LifelineModelTest {
    private LifelineModel lifelineModel;
    private Question testQuestion;

    /**
     * Teszt előkészítése
     */
    @BeforeEach
    void setUp() {
        lifelineModel = new LifelineModel();
        testQuestion = new Question.QuestionBuilder()
            .difficulty(1)
            .question("Teszt kérdés?")
            .optionA("A válasz")
            .optionB("B válasz")
            .optionC("C válasz")
            .optionD("D válasz")
            .correctAnswer("B")
            .category("TESZT")
            .build();
    }


    /**
     * A felezés segítség működésének tesztelése
     */
    @Test
    void testFiftyFifty() {
        // Első használat ellenőrzése
        Map<String, Boolean> result = lifelineModel.getFiftyFiftyResult(testQuestion);
        
        // Ellenőrizzük, hogy pontosan két válasz látható
        long visibleCount = result.values().stream().filter(visible -> visible).count();
        assertEquals(2, visibleCount);
        
        // Ellenőrizzük, hogy a helyes válasz látható maradt
        assertTrue(result.get("B"));
        
        // Ellenőrizzük, hogy a segítség használva lett
        assertFalse(lifelineModel.isFiftyFiftyAvailable());
    }


    /**
     * A felezés segítség többszöri használatának tiltása
     */
    @Test
    void testFiftyFiftyCannotBeUsedTwice() {
        // Első használat
        lifelineModel.getFiftyFiftyResult(testQuestion);
        
        // Második használat megpróbálása
        assertThrows(IllegalStateException.class, () -> {
            lifelineModel.getFiftyFiftyResult(testQuestion);
        });
    }


    /**
     * A közönség segítségének tesztelése könnyű kérdésnél (1-5. szint)
     */
    @Test
    void testAudienceHelpEasyLevel() {
        LifelineModel lifelineModel = new LifelineModel();
        Map<String, Integer> result = lifelineModel.getAudienceResult(testQuestion, 1);
        
        int correctPercentage = result.get("B");
        assertTrue(correctPercentage >= 71 && correctPercentage <= 80);
    }


    /**
     * A közönség segítségének tesztelése közepes nehézségű kérdésnél (6-10. szint)
     */
    @Test
    void testAudienceHelpMediumLevel() {
        LifelineModel lifelineModel = new LifelineModel();
        Map<String, Integer> result = lifelineModel.getAudienceResult(testQuestion, 8);
        
        int correctPercentage = result.get("B");
        assertTrue(correctPercentage >= 61 && correctPercentage <= 70);
    }


    /**
     * A közönség segítségének tesztelése nehéz kérdésnél (11-15. szint)
     */
    @Test
    void testAudienceHelpHardLevel() {
        LifelineModel lifelineModel = new LifelineModel();
        Map<String, Integer> result = lifelineModel.getAudienceResult(testQuestion, 15);
        
        int correctPercentage = result.get("B");
        assertTrue(correctPercentage >= 51 && correctPercentage <= 60);
    }


    /**
     * A közönség segítségének alapvető működésének tesztelése
     */
    @Test
    void testBasicAudienceHelp() {
        Map<String, Integer> result = lifelineModel.getAudienceResult(testQuestion, 1);
        
        // Ellenőrizzük, hogy minden válaszlehetőséghez van százalék
        assertEquals(4, result.size());
        assertTrue(result.containsKey("A"));
        assertTrue(result.containsKey("B"));
        assertTrue(result.containsKey("C"));
        assertTrue(result.containsKey("D"));
        
        // Ellenőrizzük, hogy a százalékok összege 100
        int sum = result.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals(100, sum);
    }


    /**
     * A közönség segítség többszöri használatának tiltása
     */
    @Test
    void testAudienceHelpCannotBeUsedTwice() {
        // Első használat
        lifelineModel.getAudienceResult(testQuestion, 1);
        
        // Második használat megpróbálása
        assertThrows(IllegalStateException.class, () -> {
            lifelineModel.getAudienceResult(testQuestion, 1);
        });
    }


    /**
     * A segítségek elérhetőségének ellenőrzése
     */
    @Test
    void testLifelineAvailability() {
        // Kezdetben mindkét segítség elérhető
        assertTrue(lifelineModel.isFiftyFiftyAvailable());
        assertTrue(lifelineModel.isAudienceHelpAvailable());
        
        // Felezés használata után
        lifelineModel.getFiftyFiftyResult(testQuestion);
        assertFalse(lifelineModel.isFiftyFiftyAvailable());
        assertTrue(lifelineModel.isAudienceHelpAvailable());
        
        // Közönség segítségének használata után
        lifelineModel.getAudienceResult(testQuestion, 1);
        assertFalse(lifelineModel.isFiftyFiftyAvailable());
        assertFalse(lifelineModel.isAudienceHelpAvailable());
    }

    
    /**
     * A közönség segítségének százalékos eloszlásának tesztelése
     */
    @Test
    void testAudiencePercentageDistribution() {
        // Több futtatás a véletlenszerűség tesztelésére
        for (int i = 0; i < 10; i++) {
            LifelineModel newModel = new LifelineModel();
            Map<String, Integer> result = newModel.getAudienceResult(testQuestion, 1);
            
            // Minden százalék 0 és 100 között van
            result.values().forEach(percentage -> {
                assertTrue(percentage >= 0 && percentage <= 100);
            });
            
            // A rossz válaszok százaléka mindig kisebb, mint a helyes válaszé
            int correctPercentage = result.get("B");
            result.forEach((key, value) -> {
                if (!key.equals("B")) {
                    assertTrue(value < correctPercentage);
                }
            });
        }
    }
}
