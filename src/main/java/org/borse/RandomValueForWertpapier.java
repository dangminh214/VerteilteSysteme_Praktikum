package org.borse;
import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
public class RandomValueForWertpapier {

    private static final Random random = new Random();


    public RandomValueForWertpapier() {
    }


    public static synchronized int randomizeData(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
