/** @file MainMenuModel.java */
package millionaire.model;

/**
 * @brief A főmenü modell osztálya.
 * 
 * Ez az osztály felelős a főmenü adatmodelljének kezeléséért, elsősorban a kérdések
 * tárolójának inicializálásáért és elérhetővé tételéért.
 */
public class MainMenuModel {
    private final QuestionRepository questionRepository; /**< Kérdések tárolója */
    
    /**
     * @brief Konstruktor a MainMenuModel létrehozásához
     * 
     * Inicializálja a kérdések tárolóját.
     */
    public MainMenuModel() {
        this.questionRepository = new QuestionRepository();
    }
    
    /**
     * @brief Visszaadja a kérdések tárolóját
     * @return A kérdéseket tároló QuestionRepository példány
     */
    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}