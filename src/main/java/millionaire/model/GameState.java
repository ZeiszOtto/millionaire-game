/** @file GameState.java */ 
package millionaire.model;

/**
 * @brief Enum osztály, amely a játék állapotait reprezentálja.
 */
public enum GameState {
    /**
     * Ha nem az utolsó kérdésre válaszolt a játékos, és helyes választ adott.
     * A játék folytatódik a következő kérdéssel.
     */
    CORRECT,
    
    /**
     * Ha a játékos rossz választ adott, vagy az utolsó kérdésre helytelen választ adott.
     * A játék véget ér, a játékos vagy nem nyer semmit, vagy a legutóbb elért biztos nyereményt kapja.
     */
    LOST,
    
    /**
     * A játékos a 15. kérdésre helyes választ adott, így megnyerte a főnyereményt.
     * A játék véget ér, a játékos megszerezte a főnyereményt.
     */
    WON;
    
    /**
     * @brief Megadja, hogy a játék olyan állapotban van-e, amikor véget kell érnie.
     * @return True, ha a játék véget ért, false egyébként.
     */
    public boolean isGameEnding() {
        return this == LOST || this == WON;
    }
}