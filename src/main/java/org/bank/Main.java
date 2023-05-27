package org.bank;

public class Main {
    public static void main(String [] args) throws Exception {
        Bank bank = new Bank( args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]));
        bank.start();
    }
}
