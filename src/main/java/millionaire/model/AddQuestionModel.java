/** @file AddQuestionModel.java */
package millionaire.model;

import java.util.List;

/**
 * @brief Új kérdések hozzáadását kezelő modell osztály.
 * 
 * Ez az osztály felelős az új kérdések létrehozásáért és validálásáért,
 * valamint a kérdések tárolóhoz való hozzáadásáért.
 */
public class AddQuestionModel {
    private final QuestionRepository repository;    /**< Kérdések tárolója */
    

    /**
     * @brief Konstruktor az AddQuestionModel létrehozásához
     * @param repository A kérdéseket tároló adatbázis
     */
    public AddQuestionModel(QuestionRepository repository) {
        this.repository = repository;
    }
    

    /**
     * @brief Visszaadja a kérdések nehézségeit.
     * @return A kérdések nehézségei.
     */
    public List<String> getDifficulties() {
        return QuestionRepository.DIFFICULTIES;
    }
    

    /**
     * @brief Visszaadja a kérdések kategóriáit.
     * @return A kérdések kategóriái.
     */
    public List<String> getCategories() {
        return QuestionRepository.CATEGORIES;
    }
    

    /**
     * @brief A QuestionBuilder segítségével létrehoz egy új kérdést, majd hozzáadja a kérdéseket tároló repository-hoz.
     * @param questionText A kérdés szövege
     * @param optionA A kérdéshez tartozó A válasz
     * @param optionB A kérdéshez tartozó B válasz
     * @param optionC A kérdéshez tartozó C válasz
     * @param optionD A kérdéshez tartozó D válasz
     * @param correctAnswer A helyes válasz betűjele (A, B, C vagy D)
     * @param difficulty A kérdés nehézségi szintje (1-15)
     * @param category A kérdés kategóriája
     * @return True, ha a kérdés hozzáadása a tárolóhoz sikeres, egyébként false
     * @throws NumberFormatException Ha a nehézségi szint nem alakítható számmá
     * @throws IllegalArgumentException Ha a builder érvénytelen adatokat kap
     */
    public boolean addQuestion(String questionText, String optionA, String optionB, String optionC, String optionD,
                               String correctAnswer, String difficulty, String category) {
        try {
            Question question = new Question.QuestionBuilder()
                .difficulty(Integer.parseInt(difficulty))
                .question(questionText)
                .optionA(optionA)
                .optionB(optionB)
                .optionC(optionC)
                .optionD(optionD)
                .correctAnswer(correctAnswer)
                .category(category)
                .build();
                
            repository.addQuestion(question);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

    /**
     * @brief Ellenőrzi, hogy a megadott bemeneti mezők érvényesek-e
     * 
     * Egy bemenet akkor érvényes, ha egyik kötelező mező sem üres.
     * Az üres sztringeket és a csak whitespace karaktereket tartalmazó sztringeket is üresnek tekintjük.
     * 
     * @param question A feltenni kívánt kérdés
     * @param optionA A kérdéshez tartozó A válasz
     * @param optionB A kérdéshez tartozó B válasz
     * @param optionC A kérdéshez tartozó C válasz
     * @param optionD A kérdéshez tartozó D válasz
     * @return True, ha minden szükséges bemeneti mező ki van töltve, egyébként false
     */
    public boolean validateInputs(String question, String optionA, String optionB, String optionC, String optionD) {
        return !question.trim().isEmpty() &&
               !optionA.trim().isEmpty() &&
               !optionB.trim().isEmpty() &&
               !optionC.trim().isEmpty() &&
               !optionD.trim().isEmpty();
    }
}