package org.bank;

import java.net.InetAddress;
public class Main {
    public static void main(String [] args) throws Exception {
        Bank bank = new Bank(args[0], Integer.parseInt(args[1]));
        while (true) {
            bank.receiveData();
        }
    }
}
