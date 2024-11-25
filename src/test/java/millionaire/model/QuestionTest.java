/** @file QuestionTest.java */
package millionaire.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A Question osztály tesztesetei.
 * 
 * Ez az osztály a Question osztály különböző funkcióit teszteli:
 * - Kérdés létrehozását
 * - Validációs szabályok betartását
 * - Hibakezelést
 * - Getter metódusok működését
 * 
 * @author Zeisz Ottó
 * @see Question
 */
public class QuestionTest {
    /** A tesztekhez használt építő objektum */
    private Question.QuestionBuilder builder;

    /**
     * Teszt előkészítése.
     * 
     * Minden teszt előtt létrehoz egy új QuestionBuilder példányt
     * alapértelmezett, érvényes értékekkel:
     * - nehézség: 5
     * - kérdés: "Mi a galuska másik elnevezése?"
     * - válaszok: A: makaróni, B: spagetti, C: nokedli, D: bukta
     * - helyes válasz: C
     * - kategória: KONYHA
     */
    @BeforeEach
    void setUp() {
        builder = new Question.QuestionBuilder()
            .difficulty(5)
            .question("Mi a galuska másik elnevezése?")
            .optionA("makaróni")
            .optionB("spagetti")
            .optionC("nokedli")
            .optionD("bukta")
            .correctAnswer("C")
            .category("KONYHA");
    }

    /**
     * Hiányzó kérdés szöveg tesztelése.
     * 
     * Ellenőrzi, hogy a Question osztály megfelelően kezeli-e
     * a hiányzó kérdés szöveget. A kérdés szövegének hiánya
     * IllegalStateException kivételt kell hogy eredményezzen.
     */
    @Test
    void testMissingQuestionText() {
        Question.QuestionBuilder testBuilder = new Question.QuestionBuilder()
            .difficulty(5)
            .optionA("A")
            .optionB("B")
            .optionC("C")
            .optionD("D")
            .correctAnswer("A")
            .category("TEST");

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> testBuilder.build());
        
        assertTrue(exception.getMessage().contains("Question cannot be empty"));
    }

    /**
     * Hiányzó válaszlehetőség tesztelése.
     * 
     * Ellenőrzi, hogy a Question osztály megfelelően kezeli-e
     * ha valamelyik válaszlehetőség (jelen esetben a D) hiányzik.
     * Ez IllegalStateException kivételt kell hogy eredményezzen.
     */
    @Test
    void testMissingOption() {
        Question.QuestionBuilder testBuilder = new Question.QuestionBuilder()
            .difficulty(5)
            .question("Test question")
            .optionA("A")
            .optionB("B")
            .optionC("C")
            // D válasz hiányzik
            .correctAnswer("A")
            .category("TEST");

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> testBuilder.build());
        
        assertTrue(exception.getMessage().contains("All options must be provided"));
    }

    /**
     * Érvénytelen helyes válasz tesztelése.
     * 
     * Ellenőrzi, hogy a Question osztály megfelelően kezeli-e
     * ha a megadott helyes válasz nem A, B, C vagy D.
     * Ez IllegalStateException kivételt kell hogy eredményezzen.
     */
    @Test
    void testInvalidCorrectAnswer() {
        Question.QuestionBuilder testBuilder = new Question.QuestionBuilder()
            .difficulty(5)
            .question("Test question")
            .optionA("A")
            .optionB("B")
            .optionC("C")
            .optionD("D")
            .correctAnswer("E") // Érvénytelen válasz
            .category("TEST");

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> testBuilder.build());
        
        assertTrue(exception.getMessage().contains("Correct answer must be A, B, C, or D"));
    }

    /**
     * Érvénytelen nehézségi szint tesztelése.
     * 
     * Ellenőrzi, hogy a Question osztály megfelelően kezeli-e
     * ha a megadott nehézségi szint:
     * - nagyobb mint 15 (túl magas)
     * - kisebb mint 1 (túl alacsony)
     * Mindkét esetben IllegalStateException kivételt kell dobnia.
     */
    @Test
    void testInvalidDifficultyLevel() {
        // Túl magas nehézségi szint tesztelése
        Question.QuestionBuilder highDiffBuilder = new Question.QuestionBuilder()
            .difficulty(16)
            .question("Test")
            .optionA("A")
            .optionB("B")
            .optionC("C")
            .optionD("D")
            .correctAnswer("A")
            .category("TEST");

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> highDiffBuilder.build());
        
        assertTrue(exception.getMessage().contains("Difficulty must be between 1 and 15"));

        // Túl alacsony nehézségi szint tesztelése
        Question.QuestionBuilder lowDiffBuilder = new Question.QuestionBuilder()
            .difficulty(0)
            .question("Test")
            .optionA("A")
            .optionB("B")
            .optionC("C")
            .optionD("D")
            .correctAnswer("A")
            .category("TEST");

        exception = assertThrows(IllegalStateException.class, 
            () -> lowDiffBuilder.build());
        
        assertTrue(exception.getMessage().contains("Difficulty must be between 1 and 15"));
    }

    /**
     * Érvényes kérdés létrehozásának tesztelése.
     * 
     * Ellenőrzi, hogy egy minden szempontból érvényes kérdés
     * létrehozása sikeres-e, és a getter metódusok a megfelelő
     * értékeket adják-e vissza. A teszt a setUp() metódusban
     * létrehozott alapértelmezett értékekkel dolgozik.
     */
    @Test
    void testValidQuestionCreation() {
        Question question = builder.build();
        
        assertEquals(5, question.getDifficulty());
        assertEquals("Mi a galuska másik elnevezése?", question.getQuestion());
        assertEquals("makaróni", question.getOptionA());
        assertEquals("spagetti", question.getOptionB());
        assertEquals("nokedli", question.getOptionC());
        assertEquals("bukta", question.getOptionD());
        assertEquals("C", question.getCorrectAnswer());
        assertEquals("KONYHA", question.getCategory());
    }
}