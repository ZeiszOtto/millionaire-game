/** @file QuestionRepository.java */
package millionaire.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
* @brief Kérdések perzisztens tárolását végző osztály.
* 
* Ez az osztály felelős:
* - A kérdések JSON fájlból való betöltéséért
* - A kérdések JSON fájlba mentéséért
* - A kérdések memóriában való tárolásáért
* - A kérdések szűréséért különböző szempontok alapján
*/
public class QuestionRepository {
    private static final String JSON_FILE_PATH = "src/main/resources/loim.json";    /**< A kérdéseket tároló JSON fájl útvonala */
    private final List<Question> questions;                                         /**< A memóriában tárolt kérdések listája */
    private final Gson gson;                                                        /**< JSON szerializálást végző objektum */
    

    /** @brief Elérhető kategóriák listája */
    public static final List<String> CATEGORIES = Arrays.asList(
        "TÖRTÉNELEM", "SPORT", "FÖLDRAJZ", "IRODALOM", "TUDOMÁNY", "ZENE", "FILM", "KONYHA", "ÁLTALÁNOS"
    );
    

    /** @brief Elérhető nehézségi szintek listája */
    public static final List<String> DIFFICULTIES = Arrays.asList(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
    );


    /**
    * @brief Konstruktor a QuestionRepository létrehozásához
    * 
    * Inicializálja az adattagokat és betölti a kérdéseket a JSON fájlból.
    */
    public QuestionRepository() {
        this.questions = new ArrayList<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        loadQuestions();
    }


    /**
    * @brief Betölti a kérdéseket a megadott JSON fájlból.
    * 
    * A betöltés során:
    * - Megnyitja a JSON fájlt
    * - Beolvassa és feldolgozza a JSON tömböt
    * - Minden kérdést Question objektummá alakít
    * - A kérdéseket hozzáadja a belső listához
    */
    private void loadQuestions() {
        try (Reader reader = Files.newBufferedReader(Paths.get(JSON_FILE_PATH))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement element : jsonArray) {
                JsonObject questionObj = element.getAsJsonObject();
                Question question = new Question.QuestionBuilder()
                    .difficulty(questionObj.get("Nehézség").getAsInt())
                    .question(questionObj.get("Kérdés").getAsString())
                    .optionA(questionObj.get("A").getAsString())
                    .optionB(questionObj.get("B").getAsString())
                    .optionC(questionObj.get("C").getAsString())
                    .optionD(questionObj.get("D").getAsString())
                    .correctAnswer(questionObj.get("Válasz").getAsString())
                    .category(questionObj.get("Kategória").getAsString())
                    .build();
                questions.add(question);
            }
        } catch (IOException e) {
            System.err.println("Error loading questions: " + e.getMessage());
        }
    }


    /**
    * @brief Elmenti a kérdéseket a megadott JSON fájlba.
    * 
    * A mentés során:
    * - Létrehozza a szükséges könyvtárakat
    * - Minden kérdést JSON objektummá alakít
    * - A teljes kérdéslistát JSON tömbbé fűzi
    * 
    * @throws RuntimeException Ha hiba történik a mentés során
    */
    private void saveQuestions() {
        try {
            JsonArray jsonArray = new JsonArray();
            for (Question question : questions) {
                JsonObject questionObj = new JsonObject();
                questionObj.addProperty("Nehézség", question.getDifficulty());
                questionObj.addProperty("Kérdés", question.getQuestion());
                questionObj.addProperty("A", question.getOptionA());
                questionObj.addProperty("B", question.getOptionB());
                questionObj.addProperty("C", question.getOptionC());
                questionObj.addProperty("D", question.getOptionD());
                questionObj.addProperty("Válasz", question.getCorrectAnswer());
                questionObj.addProperty("Kategória", question.getCategory());
                jsonArray.add(questionObj);
            }
            
            Files.createDirectories(Paths.get(JSON_FILE_PATH).getParent());
            Files.write(Paths.get(JSON_FILE_PATH), 
                       gson.toJson(jsonArray).getBytes(), 
                       StandardOpenOption.CREATE, 
                       StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving questions: " + e.getMessage());
        }
    }


    /**
    * @brief Hozzáad egy új kérdést a gyűjteményhez
    * @param question A hozzáadandó kérdés
    */
    public void addQuestion(Question question) {
        questions.add(question);
        saveQuestions();
    }


    /**
    * @brief Lekérdezi az adott nehézségi szintű kérdéseket
    * @param difficulty A kívánt nehézségi szint (1-15)
    * @return A megadott nehézségű kérdések listája
    */
    public List<Question> getQuestionsByDifficulty(int difficulty) {
        return questions.stream()
            .filter(q -> q.getDifficulty() == difficulty)
            .collect(Collectors.toList());
    }


    /**
    * @brief Lekérdezi az adott kategóriába tartozó kérdéseket
    * @param category A kívánt kategória neve
    * @return A kategóriához tartozó kérdések listája
    */
    public List<Question> getQuestionsByCategory(String category) {
        return questions.stream().filter(q -> q.getCategory().equals(category)).collect(Collectors.toList());
    }


    /**
    * @brief Visszaadja az összes tárolt kérdést
    * @return Az összes kérdés másolata egy új listában
    */
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }
}