package org.borse;

import java.util.Random;

public class Wertpapier {
    private static Random random = new Random();
    public String[] kurzelList = {"LSFT", "MSFT"};
    private String chosenWertpapier = kurzelList[random.nextInt(kurzelList.length)];
    private final int price = random.nextInt(500-200)+200;
    private final int quantity = random.nextInt(100-(-10))+(-10);
    String getChosenWertpapier() {
        return chosenWertpapier;
    }
    int getPrice() {
        return price;
    }
    int getQuantity() {
        return quantity;
    }
    public String createMsg() {
        String msg = chosenWertpapier + "," + quantity + "," + price;
        return msg;
    }
}



