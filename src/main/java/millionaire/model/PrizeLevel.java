/** @file PrizeLevel.java */
package millionaire.model;

/**
* @brief Nyereményszintet reprezentáló osztály.
* 
* Ez az osztály egy nyereményszintet reprezentál a játékban.
* Minden szinthez tartozik:
* - egy sorszám (1-15)
* - egy nyereményösszeg
* - egy jelző, hogy szakaszhatároló-e (garantált nyereménnyel járó szint)
*/
public class PrizeLevel {
   private final int level;        /**< A szint sorszáma (1-15) */
   private final long prize;       /**< A szinthez tartozó nyereményösszeg */
   private final boolean isSafeSpot; /**< Jelzi, hogy szakaszhatároló szint-e */
   
   /**
    * @brief Konstruktor egy nyereményszint létrehozásához
    * 
    * @param level A szint sorszáma (1-15)
    * @param prize A szinthez tartozó nyereményösszeg
    * @param isSafeSpot Igaz, ha ez egy szakaszhatároló szint (garantált nyereménnyel)
    */
   public PrizeLevel(int level, long prize, boolean isSafeSpot) {
       this.level = level;
       this.prize = prize;
       this.isSafeSpot = isSafeSpot;
   }
   
   /**
    * @brief Megadja a játékos által elért szintet.
    * @return Az elért szint.
    */
   public int getLevel() { return level; }

   /**
    * @brief Megadja a játékos által elért nyereményt.
    * @return Az elért nyeremény.
    */
   public long getPrize() { return prize; }

   /**
    * @brief Megadja, hogy a játékos által elért szint szakaszhatároló-e.
    * @return Igaz, ha a szint szakaszhatároló (garantált nyereménnyel), egyébként hamis.
    */
   public boolean isSafeSpot() { return isSafeSpot; }
}