package utils;

import java.util.Random;

public class RandomUtils {
    public int randomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public String randomStringElement(String[] from) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(from.length);
        return from[randomIndex];
    }

    protected Double randomDouble(int min, int max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
}
