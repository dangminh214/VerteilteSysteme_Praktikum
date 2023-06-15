package borse;

import java.util.Random;

public class RandomDataForWertpapierGenerator {
  /**
   * Randomly generated int.
   */

  private static final Random random = new Random();
  

  public RandomDataForWertpapierGenerator() {
  }


  public static synchronized int getData(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }

}
