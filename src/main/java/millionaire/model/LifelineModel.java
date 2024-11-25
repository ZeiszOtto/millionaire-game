/** @file LifelineModel.java */
package millionaire.model;

import java.util.*;

/**
* @brief Segítségek (lifeline) logikáját kezelő osztály.
* 
* Ez az osztály felelős a játékban használható segítségek (felezés, közönség)
* működésének kezeléséért és a válaszok generálásáért.
*/
public class LifelineModel {
    private final Random random = new Random();     /**< Véletlenszám generátor */
    private boolean fiftyFiftyUsed = false;         /**< Jelzi, hogy a felezés segítség használva volt-e */
    private boolean audienceHelpUsed = false;       /**< Jelzi, hogy a közönség segítség használva volt-e */
    

    /**
    * @brief Felezés segítség végrehajtása
    * 
    * Két rossz választ eltávolít a négy lehetőség közül, meghagyva a helyes választ
    * és egy véletlenszerűen kiválasztott rossz választ.
    * 
    * @param question Az aktuális kérdés
    * @return Map, ami tartalmazza minden válaszlehetőséghez, hogy látható-e
    * @throws IllegalStateException Ha a felezés segítséget már használták
    */
    public Map<String, Boolean> getFiftyFiftyResult(Question question) {
        if (fiftyFiftyUsed) {
            throw new IllegalStateException("Fifty-fifty lifeline already used");
        }
        
        String correctAnswer = question.getCorrectAnswer();
        Map<String, Boolean> visibilityMap = new HashMap<>();
        List<String> options = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        
        // Kivesszük a jó választ a lehetőségek közül..
        options.remove(correctAnswer);
        // Random választunk egy rossz választ amit megtartunk..
        String keepWrongAnswer = options.remove(random.nextInt(options.size()));
        
        // Beállítjuk a válaszlehetőségek láthatóságát..
        for (String option : Arrays.asList("A", "B", "C", "D")) {
            visibilityMap.put(option, option.equals(correctAnswer) || option.equals(keepWrongAnswer));
        }
        
        fiftyFiftyUsed = true;
        return visibilityMap;
    }
    

    /**
    * @brief Közönség segítségének szimulálása
    * 
    * A játék aktuális szintjétől függően generál szavazati arányokat.
    * A magasabb szinteken a közönség kevésbé biztos a helyes válaszban.
    * 
    * @param question Az aktuális kérdés
    * @param currentLevel A játék aktuális szintje
    * @return Map, ami tartalmazza a válaszlehetőségekhez tartozó százalékos arányokat
    * @throws IllegalStateException Ha a közönség segítséget már használták
    */
    public Map<String, Integer> getAudienceResult(Question question, int currentLevel) {
        if (audienceHelpUsed) {
            throw new IllegalStateException("Audience help already used");
        }
        
        int correctPercentage = calculateCorrectPercentage(currentLevel);
        Map<String, Integer> percentages = distributePercentages(question.getCorrectAnswer(), correctPercentage);
        
        audienceHelpUsed = true;
        return percentages;
    }
    

    /**
    * @brief Kiszámítja a helyes válaszra adott szavazatok százalékát
    * 
    * A játék szintjétől függően:
    * - 1-5. szint: 71-80%
    * - 6-10. szint: 61-70%
    * - 11-15. szint: 51-60%
    * 
    * @param currentLevel A játék aktuális szintje
    * @return A helyes válaszra adott szavazatok százaléka
    */
    private int calculateCorrectPercentage(int currentLevel) {
        if (currentLevel <= 5) {
            return random.nextInt(71, 81);
        } else if (currentLevel <= 10) {
            return random.nextInt(61, 71);
        } else {
            return random.nextInt(51, 61);
        }
    }
    

    /**
    * @brief Elosztja a maradék szavazatokat a helytelen válaszok között
    * 
    * @param correctAnswer A helyes válasz betűjele
    * @param correctPercentage A helyes válaszra adott szavazatok százaléka
    * @return Map, ami tartalmazza az összes válaszlehetőséghez tartozó százalékos arányt
    */
    private Map<String, Integer> distributePercentages(String correctAnswer, int correctPercentage) {
        Map<String, Integer> percentages = new LinkedHashMap<>();
        List<String> wrongAnswers = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        wrongAnswers.remove(correctAnswer);
        
        percentages.put(correctAnswer, correctPercentage);
        
        int remaining = 100 - correctPercentage;
        for (int i = 0; i < wrongAnswers.size(); i++) {
            int percentage;
            if (i == wrongAnswers.size() - 1) {
                percentage = remaining;
            } else {
                percentage = Math.min(remaining - (wrongAnswers.size() - i - 1), 
                                   random.nextInt(5, 26));
            }
            percentages.put(wrongAnswers.get(i), percentage);
            remaining -= percentage;
        }
        
        return new TreeMap<>(percentages);
    }
    

    /**
    * @brief Ellenőrzi, hogy a felezés segítség még használható-e
    * @return true ha a segítség még nem volt használva, egyébként false
    */
    public boolean isFiftyFiftyAvailable() {
        return !fiftyFiftyUsed;
    }
    

    /**
    * @brief Ellenőrzi, hogy a közönség segítség még használható-e
    * @return true ha a segítség még nem volt használva, egyébként false
    */
    public boolean isAudienceHelpAvailable() {
        return !audienceHelpUsed;
    }
}