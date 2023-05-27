package org.borse;

import java.util.Random;

public class Wertpapier {
    private CodeOfWertpapier codeOfWertpapier;
    private int quantity ;
    private int price;


    public CodeOfWertpapier getCode() {
        return codeOfWertpapier;
    }

    public void setCode(CodeOfWertpapier codeOfWertpapier) {
        this.codeOfWertpapier = codeOfWertpapier;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public Wertpapier(){
        Random random = new Random();
        this.price = RandomValueForWertpapier.randomizeData(200,400);
        this.codeOfWertpapier = CodeOfWertpapier.values()[ RandomValueForWertpapier.randomizeData(0, CodeOfWertpapier.values().length-1)];
        this.quantity = RandomValueForWertpapier.randomizeData(-20,50);
    }
    public Wertpapier(CodeOfWertpapier codeOfWertpapier, int quantity, int price){
        this.codeOfWertpapier = codeOfWertpapier;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString(){
        return quantity+"," + codeOfWertpapier.toString()+ ","+ price;
    }
}
