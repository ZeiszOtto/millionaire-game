/** @file AddQuestionController.java */
package millionaire.controller;

import millionaire.model.AddQuestionModel;
import millionaire.model.QuestionRepository;
import millionaire.view.AddQuestionView;
import java.util.List;

/**
 * @brief Új kérdések hozzáadását vezérlő osztály.
 * 
 * Ez az osztály felelős a kérdés hozzáadási folyamat vezérléséért:
 * - Kezeli a felhasználói inputot a kérdés hozzáadási nézetből
 * - Validálja a bevitt adatokat
 * - Továbbítja az adatokat a modellnek feldolgozásra
 * - Visszajelzést ad a felhasználónak a művelet eredményéről
 */
public class AddQuestionController {
    /**
     * @brief Az új kérdések kezelését végző modell
     */
    private final AddQuestionModel model;

    /**
     * @brief A kérdés hozzáadási felület nézete
     */
    private final AddQuestionView view;
    

    /**
     * @brief Konstruktor az AddQuestionController létrehozásához
     * @param repository A kérdéseket tároló adatbázis
     */
    public AddQuestionController(QuestionRepository repository) {
        this.model = new AddQuestionModel(repository);
        this.view = new AddQuestionView(this);
    }
    

    /**
     * @brief A kérdés hozzáadása gombra kattintáskor meghívott metódus.
     * 
     * Ellenőrzi a bemeneti mezők érvényességét, majd hozzáadja a kérdést a repository-hoz.
     * Sikeres hozzáadás esetén visszajelzést ad és törli a mezőket.
     * Hiba esetén hibaüzenetet jelenít meg.
     * 
     * @param question A kérdés szövege
     * @param optionA Az A válaszlehetőség
     * @param optionB A B válaszlehetőség
     * @param optionC A C válaszlehetőség
     * @param optionD A D válaszlehetőség
     * @param correctAnswer A helyes válasz betűjele
     * @param difficulty A kérdés nehézségi szintje
     * @param category A kérdés kategóriája
     */
    public void handleSubmit(String question, String optionA, String optionB, String optionC, String optionD,
                            String correctAnswer, String difficulty, String category) {
        if (!model.validateInputs(question, optionA, optionB, optionC, optionD)) {
            view.showError("Minden mezőt ki kell tölteni!");
            return;
        }
        
        boolean success = model.addQuestion(question, optionA, optionB, optionC, optionD, correctAnswer, difficulty, category);
        
        if (success) {
            view.showSuccess("A kérdés sikeresen hozzáadva!");
            view.clearFields();
        } else {
            view.showError("Hiba történt a kérdés mentése közben!");
        }
    }
    

    /**
     * @brief Bezárja a kérdés hozzáadási ablakot
     */
    public void handleCancel() {
        view.closeWindow();
    }
    

    /**
     * @brief Lekérdezi az elérhető nehézségi szinteket
     * @return A nehézségi szintek listája
     */
    public List<String> getDifficulties() {
        return model.getDifficulties();
    }
    

    /**
     * @brief Lekérdezi az elérhető kategóriákat
     * @return A kategóriák listája
     */
    public List<String> getCategories() {
        return model.getCategories();
    }
    

    /**
     * @brief Megjeleníti a kérdés hozzáadási ablakot
     */
    public void showDialog() {
        view.setVisible(true);
    }
}