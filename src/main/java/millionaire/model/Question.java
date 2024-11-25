/** @file Question.java */
package millionaire.model;

/**
* @brief Egy kérdést reprezentáló osztály.
* 
* Ez az osztály egy játékbeli kérdést reprezentál, annak minden tulajdonságával:
* - nehézségi szint (1-15)
* - kérdés szövege
* - négy válaszlehetőség (A, B, C, D)
* - helyes válasz betűjele
* - kategória
* 
* Az osztály immutable (megváltoztathatatlan), és Builder pattern segítségével példányosítható.
*/
public class Question {
    private final int difficulty;           /**< A kérdés nehézségi szintje (1-15) */
   private final String question;           /**< A kérdés szövege */
   private final String optionA;            /**< Az 'A' válaszlehetőség */
   private final String optionB;            /**< A 'B' válaszlehetőség */
   private final String optionC;            /**< A 'C' válaszlehetőség */
   private final String optionD;            /**< A 'D' válaszlehetőség */
   private final String correctAnswer;      /**< A helyes válasz betűjele (A-D) */
   private final String category;           /**< A kérdés kategóriája */


    /**
    * @brief Privát konstruktor, csak a Builder osztályon keresztül érhető el
    * @param builder A QuestionBuilder példány, ami tartalmazza az inicializálandó értékeket
    */
    private Question(QuestionBuilder builder) {
        this.difficulty = builder.difficulty;
        this.question = builder.question;
        this.optionA = builder.optionA;
        this.optionB = builder.optionB;
        this.optionC = builder.optionC;
        this.optionD = builder.optionD;
        this.correctAnswer = builder.correctAnswer;
        this.category = builder.category;
    }


    /**
     * @brief A kérdéshez tartozó nehézségi szintet adja vissza.
     * @return A nehézségi szint.
     */
    public int getDifficulty() { return difficulty; }


    /**
     * @brief A kérdés szövegét adja vissza.
     * @return A kérdés szövege.
     */
    public String getQuestion() { return question; }


    /**
     * @brief A kérdéshez tartozó 'A' válaszlehetőség szövegét adja vissza.
     * @return Az 'A' válaszlehetőség szövege.
     */
    public String getOptionA() { return optionA; }


    /**
     * @brief A kérdéshez tartozó 'B' válaszlehetőség szövegét adja vissza.
     * @return Az 'B' válaszlehetőség szövege.
     */
    public String getOptionB() { return optionB; }


    /**
     * @brief A kérdéshez tartozó 'C' válaszlehetőség szövegét adja vissza.
     * @return Az 'C' válaszlehetőség szövege.
     */
    public String getOptionC() { return optionC; }


    /**
     * @brief A kérdéshez tartozó 'D' válaszlehetőség szövegét adja vissza.
     * @return Az 'D' válaszlehetőség szövege.
     */
    public String getOptionD() { return optionD; }


    /**
     * @brief A kérdéshez tartozó helyes válasz betűjelét adja vissza.
     * @return A helyes válasz betűjele.
     */
    public String getCorrectAnswer() { return correctAnswer; }


    /**
     * @brief A kérdés kategóriáját adja vissza.
     * @return A kérdés kategóriája.
     */
    public String getCategory() { return category; }





    /**
    * @brief Builder osztály a Question példányok létrehozásához
    * 
    * Ez az osztály implementálja a Builder tervezési mintát, ami lehetővé teszi a Question objektumok 
    * rugalmas, lépésenkénti létrehozását és a kötelezőadatok validálását.
    */
    public static class QuestionBuilder {
        private int difficulty;         /**< A kérdés nehézségi szintje */
       private String question;         /**< A kérdés szövege */
       private String optionA;          /**< Az 'A' válaszlehetőség */
       private String optionB;          /**< A 'B' válaszlehetőség */
       private String optionC;          /**< A 'C' válaszlehetőség */
       private String optionD;          /**< A 'D' válaszlehetőség */
       private String correctAnswer;    /**< A helyes válasz betűjele */
       private String category;         /**< A kérdés kategóriája */


        /**
        * @brief Beállítja a kérdés nehézségi szintjét
        * @param difficulty A nehézségi szint (1-15)
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder difficulty(int difficulty) {
            this.difficulty = difficulty;
            return this;
        }
        

        /**
        * @brief Beállítja a kérdés szövegét
        * @param question A kérdés szövege
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder question(String question) {
            this.question = question;
            return this;
        }


        /**
        * @brief Beállítja az 'A' válaszlehetőséget
        * @param optionA Az 'A' válasz szövege
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder optionA(String optionA) {
            this.optionA = optionA;
            return this;
        }


        /**
        * @brief Beállítja a 'B' válaszlehetőséget
        * @param optionB A 'B' válasz szövege
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder optionB(String optionB) {
            this.optionB = optionB;
            return this;
        }


        /**
        * @brief Beállítja a 'C' válaszlehetőséget
        * @param optionC A 'C' válasz szövege
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder optionC(String optionC) {
            this.optionC = optionC;
            return this;
        }


        /**
        * @brief Beállítja a 'D' válaszlehetőséget
        * @param optionD A 'D' válasz szövege
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder optionD(String optionD) {
            this.optionD = optionD;
            return this;
        }


        /**
        * @brief Beállítja a helyes választ
        * @param correctAnswer A helyes válasz betűjele (A-D)
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder correctAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }


        /**
        * @brief Beállítja a kérdés kategóriáját
        * @param category A kérdés kategóriája
        * @return A builder példány a method chaining-hez
        */
        public QuestionBuilder category(String category) {
            this.category = category;
            return this;
        }


        /**
        * @brief Létrehozza a Question példányt
        * @return Az új Question példány
        * @throws IllegalStateException Ha valamelyik kötelező adat hiányzik vagy érvénytelen
        */
        public Question build() {
            validateQuestionData();
            return new Question(this);
        }


        /**
        * @brief Ellenőrzi a beállított adatok érvényességét
        * @throws IllegalStateException Ha valamelyik adat érvénytelen
        */
        private void validateQuestionData() {
            if (question == null || question.trim().isEmpty()) {
                throw new IllegalStateException("Question cannot be empty");
            }
            if (optionA == null || optionA.trim().isEmpty() ||
                optionB == null || optionB.trim().isEmpty() ||
                optionC == null || optionC.trim().isEmpty() ||
                optionD == null || optionD.trim().isEmpty()) {
                throw new IllegalStateException("All options must be provided");
            }
            if (correctAnswer == null || !correctAnswer.matches("[A-D]")) {
                throw new IllegalStateException("Correct answer must be A, B, C, or D");
            }
            if (difficulty < 1 || difficulty > 15) {
                throw new IllegalStateException("Difficulty must be between 1 and 15");
            }
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalStateException("Category must be provided");
            }
        }
    }
}